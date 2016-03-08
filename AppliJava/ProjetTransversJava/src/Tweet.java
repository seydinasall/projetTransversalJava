
public class Tweet {

	private String createdAt;
	private String text;
	private String name;
	private String screenName;
	private String avatar;
	
	public Tweet(String createdAt,String text,String name,
			String screenName,String avatar){
		this.createdAt = createdAt;
		this.text = text;
		this.name = name;
		this.screenName = screenName;
		this.avatar = avatar;
	}
}
