package core.entity;

import core.enums.Role;

public class User {

	private UserDetails userDetails;
	private Role role;

	/**
	 * @param userDetails
	 * @param role
	 */
	public User(UserDetails userDetails, Role role) {
		super();
		this.userDetails = userDetails;
		this.role = role;
	}

	/**
	 * @return the userDetails
	 */
	public UserDetails getUserDetails() {
		return userDetails;
	}

	/**
	 * @param userDetails the userDetails to set
	 */
	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [userDetails=").append(userDetails).append(", role=").append(role)
				.append("]");
		return builder.toString();
	}
}
