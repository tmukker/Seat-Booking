package bl.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.entity.Booking;
import core.entity.Room;
import core.entity.Seat;
import core.entity.User;

public class SeatService {

	static List<Booking> bookingList = new ArrayList<>();

	static List<Booking> resellList = new ArrayList<>();

	public static List<Booking> getBookingList() {
		return bookingList;
	}

	public static void putBookingList(Booking booking) {
		bookingList.add(booking);
	}

	public static void popBookingList(Booking booking) {
		bookingList.remove(booking);
	}

	public static List<Booking> getResellList() {
		return resellList;
	}

	public static void putResellList(Booking booking) {
		resellList.add(booking);
	}

	public static void popResellList(Booking booking) {
		resellList.remove(booking);
	}

	public static Booking getBookingById(Integer bookingId) {
		return getBookingList().stream().filter(b -> b.getId() == bookingId).findFirst().orElse(null);
	}

	public static List<Booking> getBookingsByUser(User user) {
		return getBookingList().stream()
				.filter(b -> b.getUser().getUserDetails().getEmail() == user.getUserDetails().getEmail())
				.collect(Collectors.toList());
	}

	public static Booking getResellBookingById(Integer bookingId) {
		return getResellList().stream().filter(b -> b.getId() == bookingId).findFirst().orElse(null);
	}

	public static List<Booking> getResellBookingsByUser(User user) {
		return getResellList().stream()
				.filter(b -> b.getUser().getUserDetails().getEmail() == user.getUserDetails().getEmail())
				.collect(Collectors.toList());
	}

	public static List<Booking> getResellPostingsExceptUser(User user) {
		return getResellList().stream()
				.filter(b -> b.getUser().getUserDetails().getEmail() != user.getUserDetails().getEmail())
				.collect(Collectors.toList());
	}

	/**
	 * To book a seat so that clients can make a booking
	 * @param loggedInUser User who is making a booking
	 * @param chosenRoom Room in which the seat is
	 * @param row Row of the seat
	 * @param column Column of the seat
	 * @return Booking ID of the room
	 */
	public static Integer bookSeat(User loggedInUser, Room chosenRoom, int row, int column) {
		Seat seat = new Seat(row, column, chosenRoom);
		Double regularPrice = chosenRoom.getRegularPrice();
		Integer sizeOfRoom = chosenRoom.getSize();
		Double price = regularPrice;
		if (row == 0) {
			price = 2 * regularPrice;
		} else if (row == sizeOfRoom - 1) {
			price = 0.75 * regularPrice;
		} else if (column == ((sizeOfRoom - 1) / 2) || column == ((sizeOfRoom) / 2)) {
			price = 1.25 * regularPrice;
		} else {
			price = regularPrice;
		}

		Booking booking = new Booking(seat, price, LocalDateTime.now(), loggedInUser, null);
		putBookingList(booking);
		return booking.getId();
	}

	/**
	 * Displays all of the available and booked seats of a room
	 * @param room The room whose seats are to be viewed
	 */
	public static void showSeatsByRoom(Room room) {
		for (int i = 0; i < room.getSize(); i++) {
			for (int j = 0; j < room.getSize(); j++) {
				final int li = i, lj = j;
				Booking seatInBookingList = getBookingList().stream()
						.filter(b -> b.getSeat().getRow() == li && b.getSeat().getColumn() == lj).findAny()
						.orElse(null);
				if (seatInBookingList == null) {
					System.out.print("(" + i + "," + j + ")  ");
				} else {
					System.out.print("[X,X]  ");
				}
			}
			System.out.println();
		}
	}
}
