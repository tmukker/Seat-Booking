package core.entity;

public class Message {

	private String title;

	private String desc;

	private User user1;

	private User user2;

	public Message(String title, String desc, User user1, User user2) {
		super();
		this.title = title;
		this.desc = desc;
		this.user1 = user1;
		this.user2 = user2;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the user1
	 */
	public User getUser1() {
		return user1;
	}

	/**
	 * @param user1 the user1 to set
	 */
	public void setUser1(User user1) {
		this.user1 = user1;
	}

	/**
	 * @return the user2
	 */
	public User getUser2() {
		return user2;
	}

	/**
	 * @param user2 the user2 to set
	 */
	public void setUser2(User user2) {
		this.user2 = user2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [title=").append(title).append(", desc=").append(desc)
				.append(", user1=").append(user1).append(", user2=").append(user2).append("]");
		return builder.toString();
	}
}
