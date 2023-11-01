package core.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Booking {
	private static final AtomicInteger count = new AtomicInteger(0);

	private Integer id;

	private Seat seat;

	private Double price;

	private LocalDateTime timestamp;

	private User user;

	private List<Bid> resellBids;
	
	public Booking(Seat seat, Double price, LocalDateTime timestamp, User user,
			List<Bid> resellBids) {
		super();
		this.id = count.incrementAndGet();
		this.seat = seat;
		this.price = price;
		this.timestamp = timestamp;
		this.user = user;
		this.resellBids = resellBids;
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

	/**
	 * @return the seat
	 */
	public Seat getSeat() {
		return seat;
	}

	/**
	 * @param seat the seat to set
	 */
	public void setSeat(Seat seat) {
		this.seat = seat;
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

	/**
	 * @return the timestamp
	 */
	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp the timestamp to set
	 */
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}
	
	public List<Bid> getResellBids() {
		return resellBids;
	}

	public void setResellBids(List<Bid> resellBids) {
		this.resellBids = resellBids;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Booking [id=").append(id).append(", seat=").append(seat).append(", price=")
				.append(price).append(", timestamp=").append(timestamp).append(", user=").append(user)
				.append(", resellBids=").append(resellBids).append("]");
		return builder.toString();
	}
}
