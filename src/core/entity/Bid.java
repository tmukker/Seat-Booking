package core.entity;

public class Bid {

	UserDetails userDetails;
	Double price;

	public Bid(UserDetails userDetails, Double price) {
		super();
		this.userDetails = userDetails;
		this.price = price;
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
	 * @return the price
	 */
	public Double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(Double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Bid [userDetails=").append(userDetails).append(", price=").append(price)
				.append("]");
		return builder.toString();
	}
}
