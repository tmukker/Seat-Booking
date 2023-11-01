package core.entity;

import java.util.concurrent.atomic.AtomicInteger;

import core.enums.Role;

public class Complaint extends Message {
	private static final AtomicInteger count = new AtomicInteger(0);

	private Integer id;

	public Complaint(String title, String desc, User user) {
		super(title, desc, user,
				new User(new UserDetails("Admin", "User", "admin@soen.com", "admin"), Role.ADMIN));
		this.id = count.incrementAndGet();
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Complaint [getId()=").append(getId()).append(", getTitle()=").append(getTitle())
				.append(", getDesc()=").append(getDesc()).append(", getUser1()=").append(getUser1())
				.append(", getUser2()=").append(getUser2()).append("]");
		return builder.toString();
	}

}
