package com.clement.api;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.impl.DefaultHttpClientConnection;
import org.apache.http.message.BasicHttpEntityEnclosingRequest;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.ImmutableHttpProcessor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


public class Api {
	
	
	private String twitterConsumerKey = "DoZ2bSvA5pAniS8kDxJJ8KGsu";
	private String twitterConsumerSecret = "fDQon1kUoffYBzls52iOGeeSUKY6USBcSN0CMbLjCk4CGc7djs";	
	private String twitterAccesToken = "706818055054233600-axPLau20pgEjl752BmJRsVx0Fzaa6ON";
	private String twitterAccesSecret = "TLQ5uK4KK2ZTWnpMA1I8lA6m5IyuoPdCiJWBiB16wNKpz";
	
	
	public String getTwitterConsumerKey() {
		return twitterConsumerKey;
	}
	public String getTwitterConsumerSecret() {
		return twitterConsumerSecret;
	}
	public String getTwitterAccesToken() {
		return twitterAccesToken;
	}
	public String getTwitterAccesSecret() {
		return twitterAccesSecret;
	}
	public String encode(String value) 
	{
        String encoded = null;
        try {
            encoded = URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException ignore) {
        }
        StringBuilder buf = new StringBuilder(encoded.length());
        char focus;
        for (int i = 0; i < encoded.length(); i++) {
            focus = encoded.charAt(i);
            if (focus == '*') {
                buf.append("%2A");
            } else if (focus == '+') {
                buf.append("%20");
            } else if (focus == '%' && (i + 1) < encoded.length()
                    && encoded.charAt(i + 1) == '7' && encoded.charAt(i + 2) == 'E') {
                buf.append('~');
                i += 2;
            } else {
                buf.append(focus);
            }
        }
        return buf.toString();
    }
	
	private static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException 
	{
	    SecretKey secretKey = null;

	    byte[] keyBytes = keyString.getBytes();
	    secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

	    Mac mac = Mac.getInstance("HmacSHA1");
	    mac.init(secretKey);

	    byte[] text = baseString.getBytes();

	    return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
	}
	
	public String generateAuthNonce(){
		
		String uuid_string = UUID.randomUUID().toString();
		uuid_string = uuid_string.replaceAll("-", "");
		String oauth_nonce = uuid_string;
		return oauth_nonce;
	}
	
	public String generateTimestamp(){
		Calendar tempcal = Calendar.getInstance();
		long ts = tempcal.getTimeInMillis();// get current time in milliseconds
		String oauthTimestamp = (new Long(ts/1000)).toString(); // then divide by 1000 to get seconds
		return oauthTimestamp;
	}
	
	public String generateSignature(String oauthSignature, String signatureBaseString){
		try {
			oauthSignature = computeSignature(signatureBaseString, twitterConsumerSecret + "&" + encode(twitterAccesSecret));  // note the & at the end. Normally the user access_token would go here, but we don't know it yet for request_token
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oauthSignature;
	}
	
	public String generateHeader(String oauthTimestamp, String oauthNonce, 
			String oauthSignature){
		
		String AuthHeader = "OAuth oauth_consumer_key=\"" +
				twitterConsumerKey + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" 
			+ oauthTimestamp + "\",oauth_nonce=\"" + oauthNonce + 
			"\",oauth_version=\"1.0\",oauth_signature=\"" + encode(oauthSignature) +
			"\",oauth_token=\"" + encode(twitterAccesToken) + "\"";
		
		return AuthHeader;
	}
	
	//send search request an return it in JSONArray
	public JSONArray searchTweets(String q){
		
		JSONObject jsonresponse = new JSONObject();
		
		JSONArray ja = null;
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";
		
		// generate authorization header
		String oauth_nonce = generateAuthNonce();
		
		// get the timestamp
		String oauth_timestamp = generateTimestamp();

		//generate signature
		String parameter_string = "lang=en&oauth_consumer_key=" + twitterConsumerKey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
				"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + encode(twitterAccesToken) + "&oauth_version=1.0&q=" + encode(q) + "&result_type=mixed";	
		String twitterURL = "https://api.twitter.com/1.1/search/tweets.json";
		String twitterURLHost = "api.twitter.com";
		String twitterURLPath = "/1.1/search/tweets.json";
		String signature_base_string = get_or_post + "&"+ encode(twitterURL) + "&" + encode(parameter_string);
				
		String oauth_signature = null;
		oauth_signature = generateSignature( oauth_signature, signature_base_string);
		
		
		String authorization_header_string = "OAuth oauth_consumer_key=\"" + twitterConsumerKey + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + oauth_timestamp + 
				"\",oauth_nonce=\"" + oauth_nonce + "\",oauth_version=\"1.0\",oauth_signature=\"" + encode(oauth_signature) + "\",oauth_token=\"" + encode(twitterAccesToken) + "\"";

		/*
		try{
			URL url;
			url = new URL(twitterURL);
			HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
	        connect.setDoInput(true);
	        connect.setDoOutput(true);
	        connect.setRequestProperty("Authorization", authorization_header_string);
	        connect.setRequestMethod("GET");
	        connect.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connect.addRequestProperty("Host", twitterURLHost);
	        
	     // if successful, the response should be a JSONONArray of tweets
	        BufferedReader br = new BufferedReader(  new InputStreamReader(  
                    url.openConnection().getInputStream())); 
	        StringBuffer buff = new StringBuffer();  

	        int c;  
	        while((c=br.read())!=-1)  
	        {  
	            buff.append((char)c);  
	        }  
	        br.close();  
	        JSONObject js = new JSONObject(buff.toString());  
	        ja = js.getJSONArray("statuses");  */
	        
	        /*JSONObject jo = new JSONObject(EntityUtils.toString(response.getEntity()));
			 System.out.println("debut");
			 System.out.println(jo.toString());
			 ja = jo.getJSONArray("statuses");
	        
		}catch(Exception e){
			e.printStackTrace();
		}
		return ja;
	}*/
		 //set basic parameters for http request
		 HttpParams params = new SyncBasicHttpParams();
		 HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		 HttpProtocolParams.setContentCharset(params, "UTF-8");
		 HttpProtocolParams.setUserAgent(params, "HttpCore/1.1");
		 HttpProtocolParams.setUseExpectContinue(params, false);

		 HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
	                // Required protocol interceptors
	                new RequestContent(),
	                new RequestTargetHost(),
	                // Recommended protocol interceptors
	                new RequestConnControl(),
	                new RequestUserAgent(),
	                new RequestExpectContinue()});

		 HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
		 HttpContext context = new BasicHttpContext(null);
		 HttpHost host = new HttpHost(twitterURLHost,443);
		 DefaultHttpClientConnection conn = new DefaultHttpClientConnection();

		 context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
		 context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);

		 try {
			 try {
				 //need https, we send ssl to get the certificate
				 SSLContext sslcontext = SSLContext.getInstance("TLS");
				 sslcontext.init(null, null, null);
				 SSLSocketFactory ssf = sslcontext.getSocketFactory();
				 Socket socket = ssf.createSocket();
				 socket.connect(
				   new InetSocketAddress(host.getHostName(), host.getPort()), 0);
				 conn.bind(socket, params);
				 
				 //get tweets of research
				 BasicHttpEntityEnclosingRequest request2 = new BasicHttpEntityEnclosingRequest("GET", twitterURLPath + "?lang=en&result_type=mixed&q=" + encode(q));
				 request2.setParams(params);
				 request2.addHeader("Authorization", authorization_header_string); // always add the Authorization header
				 httpexecutor.preProcess(request2, httpproc, context);
				 HttpResponse response2 = httpexecutor.execute(request2, conn, context);
				 response2.setParams(params);
				 httpexecutor.postProcess(response2, httpproc, context);

				 if(response2.getStatusLine().toString().indexOf("500") != -1)
				 {
					 jsonresponse.put("response_status", "error");
					 jsonresponse.put("message", "Twitter auth error.");
				 }
				 else
				 {
					 // if successful, the response should be a JSONONArray of tweets 
					 JSONObject jo = new JSONObject(EntityUtils.toString(response2.getEntity()));
					 System.out.println(response2.getEntity());
					 System.out.println(jo.toString());
					 ja = jo.getJSONArray("statuses");
					  
					 conn.close();
				 }   
			 }
			 catch(Exception e) 
			 {	
				 e.printStackTrace();
			 } 
			 	
			 finally {
				 conn.close();
			 }
		 } 
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }
		 return ja;
	}
	
	
	//send timeline request an return timeline in String
	public JSONArray getTimeline(){
			
			JSONObject jsonresponse = new JSONObject();
			JSONArray ja = null;
			String get_or_post = "GET";
			String oauth_signature_method = "HMAC-SHA1";
		try{
			
			
			// generate auth_nonce
			String oauth_nonce = generateAuthNonce();
			
			// get the timestamp
			String oauth_timestamp = generateTimestamp();
	
			//encode URL
			String parameter_string = "oauth_consumer_key=" + twitterConsumerKey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
				"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + encode(twitterAccesToken) + "&oauth_version=1.0";	
			String twitterURL = "https://api.twitter.com/1.1/statuses/home_timeline.json";
			String twitterURLHost = "api.twitter.com";
			String twitterURLPath = "/1.1/statuses/home_timeline.json";
			String signature_base_string = get_or_post + "&"+ encode(twitterURL) + "&" + encode(parameter_string);
			
			String oauth_signature = "";
			
			//get the signature
			oauth_signature = generateSignature(oauth_signature, signature_base_string);
			
			//generate header
			String authorization_header_string = generateHeader(
					oauth_timestamp, oauth_nonce, oauth_signature);
			/*
			try{
				URL url;
				url = new URL(twitterURL);
				HttpsURLConnection connect = (HttpsURLConnection) url.openConnection();
		        connect.setDoInput(true);
		        connect.setDoOutput(true);
		        connect.setRequestProperty("Authorization", authorization_header_string);
		        connect.setRequestMethod("GET");
		        connect.addRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        connect.addRequestProperty("Host", twitterURLHost);
		        
		     // if successful, the response should be a JSONONArray of tweets
		        BufferedReader br = new BufferedReader(new InputStreamReader(  
	                    url.openConnection().getInputStream())); 
		        StringBuffer buff = new StringBuffer();  

		        int c;  
		        while((c=br.read())!=-1)  
		        {  
		            buff.append((char)c);  
		        }  
		        br.close();  
		        JSONArray js = new JSONArray(buff.toString());  
		         
			}catch(Exception e){
				e.printStackTrace();
			}
			*/
			 //set basic parameters for http request
			 HttpParams params = new SyncBasicHttpParams();
			 HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			 HttpProtocolParams.setContentCharset(params, "UTF-8");
			 HttpProtocolParams.setUserAgent(params, "HttpCore/1.1");
			 HttpProtocolParams.setUseExpectContinue(params, false);
	
			 HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
		                // Required protocol interceptors
		                new RequestContent(),
		                new RequestTargetHost(),
		                // Recommended protocol interceptors
		                new RequestConnControl(),
		                new RequestUserAgent(),
		                new RequestExpectContinue()});
	
			 HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
			 HttpContext context = new BasicHttpContext(null);
			 HttpHost host = new HttpHost(twitterURLHost,443);
			 DefaultHttpClientConnection conn = new DefaultHttpClientConnection();
	
			 context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
			 context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);
	
			 try {
				 try {
					 //need https, we send ssl to get the certificate
					 SSLContext sslcontext = SSLContext.getInstance("TLS");
					 sslcontext.init(null, null, null);
					 SSLSocketFactory ssf = sslcontext.getSocketFactory();
					 Socket socket = ssf.createSocket();
					 socket.connect( new InetSocketAddress(host.getHostName(), host.getPort()), 0);
					 conn.bind(socket, params);
	
					 
					 //generate request timeline
					 BasicHttpEntityEnclosingRequest request2 = new BasicHttpEntityEnclosingRequest("GET", twitterURLPath);
					 request2.setParams(params);
					 request2.addHeader("Authorization", authorization_header_string); // always add the Authorization header
					 httpexecutor.preProcess(request2, httpproc, context);
					 HttpResponse response2 = httpexecutor.execute(request2, conn, context);
					 response2.setParams(params);
					 httpexecutor.postProcess(response2, httpproc, context);
	
					 if(response2.getStatusLine().toString().indexOf("500") != -1)
					 {
						 jsonresponse.put("response_status", "error");
						 jsonresponse.put("message", "Twitter auth error.");
					 }
					 else
					 {
						 // if successful, return String with timeline
						 String str = EntityUtils.toString(response2.getEntity());
						 System.out.println(str);
						 ja = new JSONArray(str);
						 
						 conn.close();
					 }   
				 }
				 catch(Exception e) 
				 {	
					e.printStackTrace();
				 }
				 finally {
					 conn.close();
				 }
			 } 
			 catch(Exception e)
			 {
				 e.printStackTrace();
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
		 return ja;
	}
	
	
	public String getAccount(){
		
		JSONObject jsonresponse = new JSONObject();
		String str = null;
		String get_or_post = "GET";
		String oauth_signature_method = "HMAC-SHA1";
			try{
				
				
				// generate auth_nonce
				String oauth_nonce = generateAuthNonce();
				
				// get the timestamp
				String oauth_timestamp = generateTimestamp();
		
				//encode URL
				String parameter_string = "oauth_consumer_key=" + twitterConsumerKey + "&oauth_nonce=" + oauth_nonce + "&oauth_signature_method=" + oauth_signature_method + 
					"&oauth_timestamp=" + oauth_timestamp + "&oauth_token=" + encode(twitterAccesToken) + "&oauth_version=1.0";	
				String twitterURL = "https://api.twitter.com/1.1/account/verify_credentials.json";
				String twitterURLHost = "api.twitter.com";
				String twitterURLPath = "/1.1/account/verify_credentials.json";
				String signature_base_string = get_or_post + "&"+ encode(twitterURL) + "&" + encode(parameter_string);
				
				String oauth_signature = "";
				
				//get the signature
				oauth_signature = generateSignature(oauth_signature, signature_base_string);
				
				//generate header
				String authorization_header_string = generateHeader(
						oauth_timestamp, oauth_nonce, oauth_signature);
		
				 //set basic parameters for http request
				 HttpParams params = new SyncBasicHttpParams();
				 HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				 HttpProtocolParams.setContentCharset(params, "UTF-8");
				 HttpProtocolParams.setUserAgent(params, "HttpCore/1.1");
				 HttpProtocolParams.setUseExpectContinue(params, false);
		
				 HttpProcessor httpproc = new ImmutableHttpProcessor(new HttpRequestInterceptor[] {
			                // Required protocol interceptors
			                new RequestContent(),
			                new RequestTargetHost(),
			                // Recommended protocol interceptors
			                new RequestConnControl(),
			                new RequestUserAgent(),
			                new RequestExpectContinue()});
		
				 HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
				 HttpContext context = new BasicHttpContext(null);
				 HttpHost host = new HttpHost(twitterURLHost,443);
				 DefaultHttpClientConnection conn = new DefaultHttpClientConnection();
		
				 context.setAttribute(ExecutionContext.HTTP_CONNECTION, conn);
				 context.setAttribute(ExecutionContext.HTTP_TARGET_HOST, host);
		
				 try {
					 try {
						 //need https, we send ssl to get the certificate
						 SSLContext sslcontext = SSLContext.getInstance("TLS");
						 sslcontext.init(null, null, null);
						 SSLSocketFactory ssf = sslcontext.getSocketFactory();
						 Socket socket = ssf.createSocket();
						 socket.connect( new InetSocketAddress(host.getHostName(), host.getPort()), 0);
						 conn.bind(socket, params);
		
						 
						 //generate request timeline
						 BasicHttpEntityEnclosingRequest request2 = new BasicHttpEntityEnclosingRequest("GET", twitterURLPath);
						 request2.setParams(params);
						 request2.addHeader("Authorization", authorization_header_string); // always add the Authorization header
						 httpexecutor.preProcess(request2, httpproc, context);
						 HttpResponse response2 = httpexecutor.execute(request2, conn, context);
						 response2.setParams(params);
						 httpexecutor.postProcess(response2, httpproc, context);
		
						 if(response2.getStatusLine().toString().indexOf("500") != -1)
						 {
							 jsonresponse.put("response_status", "error");
							 jsonresponse.put("message", "Twitter auth error.");
						 }
						 else
						 {
							 // if successful, return String with timeline
							 str = EntityUtils.toString(response2.getEntity());
							 
							 
							 conn.close();
						 }   
					 }
					 catch(Exception e) 
					 {	
						e.printStackTrace();
					 }
					 finally {
						 conn.close();
					 }
				 } 
				 catch(Exception e)
				 {
					 e.printStackTrace();
				 }
			}catch(Exception e){
				e.printStackTrace();
			}
	 return str;
	 }
}	