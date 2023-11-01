package bl.booking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.entity.Booking;
import core.entity.ExchangeRequest;
import core.entity.User;

public class ExchangeService {

	static List<ExchangeRequest> exchangeList = new ArrayList<>();

	public static List<ExchangeRequest> getExchangeList() {
		return exchangeList;
	}

	public static void putExchangeList(ExchangeRequest request) {
		exchangeList.add(request);
	}

	/**
	 * Find the exchange requests received by the user
	 * @param user User whose requests are to be found
	 * @return
	 */
	public static List<ExchangeRequest> getExchangeRequestsReceviedByUser(User user) {
		return exchangeList.stream().filter(e -> e.getBooking2().getUser().getUserDetails()
				.getEmail() == user.getUserDetails().getEmail()).collect(Collectors.toList());
	}

	/**
	 * Initiate a request to exchange the bookings 
	 * @param booking1 First booking
	 * @param booking2 Second booking
	 * @return the exchange request created
	 */
	public static ExchangeRequest createExchangeRequest(Booking booking1, Booking booking2) {
		ExchangeRequest request = new ExchangeRequest(booking1, booking2);
		putExchangeList(request);
		return request;
	}

	/**
	 * Processes and completes the exchange request
	 * @param request The exchange request to be completed
	 */
	public static void fulfilExchangeRequest(ExchangeRequest request) {
		Booking booking1 = request.getBooking1();
		Booking booking2 = request.getBooking2();
		SeatService.popBookingList(booking1);
		SeatService.popBookingList(booking2);
		User user1 = booking1.getUser();
		booking1.setUser(booking2.getUser());
		booking2.setUser(user1);
		SeatService.putBookingList(booking1);
		SeatService.putBookingList(booking2);
	}
}
