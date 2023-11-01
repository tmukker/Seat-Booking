package core.entity;

public class ExchangeRequest {

	Booking booking1;
	Booking booking2;

	public ExchangeRequest(Booking booking1, Booking booking2) {
		super();
		this.booking1 = booking1;
		this.booking2 = booking2;
	}

	/**
	 * @return the booking1
	 */
	public Booking getBooking1() {
		return booking1;
	}

	/**
	 * @param booking1 the booking1 to set
	 */
	public void setBooking1(Booking booking1) {
		this.booking1 = booking1;
	}

	/**
	 * @return the booking2
	 */
	public Booking getBooking2() {
		return booking2;
	}

	/**
	 * @param booking2 the booking2 to set
	 */
	public void setBooking2(Booking booking2) {
		this.booking2 = booking2;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ExchangeRequest [booking1=").append(booking1).append(", booking2=")
				.append(booking2).append("]");
		return builder.toString();
	}
}
