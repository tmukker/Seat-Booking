package bl.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import bl.booking.ExchangeService;
import bl.booking.RoomService;
import bl.booking.SeatService;
import bl.messaging.ComplaintService;
import bl.messaging.MessagingService;
import core.entity.Bid;
import core.entity.Booking;
import core.entity.ExchangeRequest;
import core.entity.Room;
import core.entity.User;
import core.enums.Role;

public class ClientService extends UserService {

	@Override
	public void menu() {
		Boolean isLoggedIn = true;
		showNotifications();
		while (isLoggedIn) {
			System.out.println("✦•······················•✦•······················•✦");
			System.out.println("Please choose an option from the below menu");

			System.out.println("1. Book a seat");
			System.out.println("2. Display my bookings");
			System.out.println("3. Resell a seat");
			System.out.println("4. Cancel my booking");
			System.out.println("5. Messenger");
			System.out.println("6. Complain about a booking");
			System.out.println("7. Logout");

			Scanner scanner = new Scanner(System.in);
			System.out.println("Press an option: ");
			Integer option = scanner.nextInt();
//		scanner.nextLine();
			switch (option) {
			case 1:
				System.out.println("1. Book a new seat");
				System.out.println("2. Book from resell list");
				System.out.println("3. Exhange my seat");
				Integer typeOfBooking = scanner.nextInt();
				switch (typeOfBooking) {
				case 1:
					bookANewSeat();
					break;
				case 2:
					bookFromResellList();
					break;
				case 3:
					bookViaExchanging();
					break;
				default:
					System.out.println("Wrong input");
				}
				break;
			case 2:
				List<Booking> myBookings = SeatService.getBookingsByUser(loggedInUser);
				for (Booking myBooking : myBookings) {
					System.out.println("ID: " + myBooking.getId() + " for Seat No. ("
							+ myBooking.getSeat().getRow() + "," + myBooking.getSeat().getColumn()
							+ ") and Room No " + myBooking.getSeat().getRoom().getId() + " at "
							+ myBooking.getSeat().getRoom().getBeginTimeStamp());
				}
				break;
			case 3:
				System.out.println("1. Post a seat to resell");
				System.out.println("2. Check and approve existing listings");

				Integer choiceInReselling = scanner.nextInt();

				switch (choiceInReselling) {
				case 1:
					System.out.println("Enter the booking id");
					Integer inputBookingId = scanner.nextInt();
					Booking inputBooking = SeatService.getBookingById(inputBookingId);
					Double newPrice = calculateNewPrice(inputBooking.getPrice());
					SeatService.popBookingList(inputBooking);
					inputBooking.setPrice(newPrice);
					SeatService.putResellList(inputBooking);
					System.out
							.println("Your booking with id " + inputBookingId + " has been posted for reselling");
					break;
				case 2:
					checkAndApproveExistingRequests();
					break;
				default:
					System.out.println("Wrong choice");
				}
				break;
			case 4:
				System.out.println("Enter the ID of the booking you wish to cancel");
				Integer bId = scanner.nextInt();
				SeatService.popBookingList(SeatService.getBookingById(bId));
				System.out.println("Your booking with ID " + bId + " has been cancelled");
				break;
			case 5:
				System.out.println("Enter 1 to send a new message and 2 to check incoming messages");
				Integer messagingOption = scanner.nextInt();
				if (messagingOption == 1) {
					System.out.println("Enter email id of user you would like to communicate with");
					scanner.nextLine();
					String email = scanner.nextLine();
					User userToSendMsg = getUserByEmail(email);
					System.out.println("Enter the message to be sent");
					String message = scanner.nextLine();
					MessagingService.sendMessage(loggedInUser, userToSendMsg, message);
					System.out.println("Your message has been sent");
				} else if (messagingOption == 2) {
					MessagingService.displayMessages(loggedInUser);
				} else {
					System.out.println("Please enter 1 or 2");
				}
				break;
			case 6:
				System.out.println("Please enter the booking ID against which you would like to complain");
				Integer complainBookingId = scanner.nextInt();
				Booking complainBooking = SeatService.getBookingById(complainBookingId);
				System.out.println("Kindly describe about your complaint");
				scanner.nextLine();
				String desc = scanner.nextLine();
				Integer complaintID = ComplaintService.registerComplaint(loggedInUser, complainBooking,
						desc);
				System.out.println("Your complaint has been registered with ID " + complaintID);
				break;
			case 7:
				isLoggedIn = false;
				logout();
				break;
			default:
				System.out.println("Wrong option!");
				break;
			}
		}
	}

	/**
	 * Method to check all the resell requests, and approve if required
	 */
	private void checkAndApproveExistingRequests() {
		Scanner scanner = new Scanner(System.in);
		List<Booking> resellBookings = SeatService.getResellBookingsByUser(loggedInUser);
		System.out.println("You have posted the following bookings for reselling");
		for (Booking resellBooking : resellBookings) {
			System.out.println("ID: " + resellBooking.getId() + " for " + resellBooking.getPrice());
		}
		System.out.println("Please enter the booking ID to further check the status");
		Integer interestedBookingId = scanner.nextInt();
		Booking interestedBooking = SeatService.getResellBookingById(interestedBookingId);

		System.out.println("The following bids have been placed on this booking");
		Integer i = 1;
		for (Bid resellBid : interestedBooking.getResellBids()) {
			System.out.println(
					i++ + ". $" + resellBid.getPrice() + " by " + resellBid.getUserDetails().getFirstName());
		}
		System.out.println("Choose the number that you want to go ahead with, else press X");
		try {
			Integer chosenBidId = scanner.nextInt();
			Bid approvedBid = interestedBooking.getResellBids().get(chosenBidId - 1);

			SeatService.popResellList(interestedBooking);
			interestedBooking.setUser(new User(approvedBid.getUserDetails(), Role.CLIENT));
			SeatService.putBookingList(interestedBooking);
			System.out.println("Your seat has been sold");
		} catch (Exception e) {
			// user enters anything except an integer
			// do nothing, and flow will restart
		}

	}

	private void bookViaExchanging() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Make a new request");
		System.out.println("2. Check existing requests");
		Integer exchangeMethod = scanner.nextInt();
		if (exchangeMethod == 1) {
			System.out.println("Enter ID of your booking you would like to exchange");
			Integer ownBookingId = scanner.nextInt();
			Booking ownBooking = SeatService.getBookingById(ownBookingId);
			System.out.println("Following bookings are available from other users");
			List<Booking> otherBookings = SeatService.getBookingList().stream().filter(
					b -> b.getUser().getUserDetails().getEmail() != loggedInUser.getUserDetails().getEmail())
					.collect(Collectors.toList());
			for (Booking booking : otherBookings) {
				System.out.println("ID: " + booking.getId() + " for Seat No. (" + booking.getSeat().getRow()
						+ "," + booking.getSeat().getColumn() + ") and Room No "
						+ booking.getSeat().getRoom().getId() + " at "
						+ booking.getSeat().getRoom().getBeginTimeStamp());
			}
			System.out.println("Choose a booking ID to send an exhange request to its owner");
			Integer interestedBookingIdToExchange = scanner.nextInt();
			Booking interestedBooking = SeatService.getBookingById(interestedBookingIdToExchange);
			ExchangeService.createExchangeRequest(ownBooking, interestedBooking);
			System.out.println("Your exchange request has been created");
		} else if (exchangeMethod == 2) {
			List<ExchangeRequest> requestsReceived = ExchangeService
					.getExchangeRequestsReceviedByUser(loggedInUser);
			if (requestsReceived.isEmpty()) {
				System.out.println("You do not have incoming exchange requests");
				return;
			}
			System.out.println("You have recevied the follwing exchange requests");
			Integer i = 1;
			for (ExchangeRequest request : requestsReceived) {
				System.out.println(i++ + " - Booking Id: " + request.getBooking1().getId()
						+ " to exchange with your Booking ID: " + request.getBooking2().getId());
			}
			System.out.println("Enter the serial number you want to continue with, or press X");
			try {
				Integer chosenExchangeReqId = scanner.nextInt();
				ExchangeRequest chosenExchangeReq = requestsReceived.get(chosenExchangeReqId - 1);
				ExchangeService.fulfilExchangeRequest(chosenExchangeReq);
				System.out.println("Your booking has been exchanged");
			} catch (Exception e) {
				// do nothing
			}
		}

	}

	private void showNotifications() {
		System.out.println("————— ୨୧ —————");
		System.out.println("NOTIFICATIONS");
		System.out.println("————— ୨୧ —————");
		System.out.println("Following bookings are available on resell list");
		showResellBookings();
		System.out.println("\nHit Enter to continue");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}

	private Double calculateNewPrice(Double oldPrice) {
		System.out.println("Either enter the new price or the discount ratio in %");
		Scanner scanner = new Scanner(System.in);
		String newValue = scanner.nextLine();
		Double newPrice = oldPrice;
		if (newValue.contains("%")) {
			Double discountRatio = (Double.parseDouble(newValue.substring(0, newValue.length() - 1)))
					/ 100;
			newPrice = oldPrice * (1 - (discountRatio));
		} else {
			newPrice = Double.parseDouble(newValue);
		}
		return newPrice;
	}

	private void bookFromResellList() {
		Scanner scanner = new Scanner(System.in);
		System.out.println();
		System.out.println("The following seats are available in resell list");
		showResellBookings();
		System.out.println("Enter the Booking ID you are interested in");
		Integer bookingId = scanner.nextInt();
		Booking booking = SeatService.getResellBookingById(bookingId);

		if (booking == null) {
			System.out.println("Error occurred. Please try again.");
			return;
		}

		System.out.println("Do you want to book it or negotiate further?");
		System.out.println("Enter B to book and N to negotiate");
		scanner.nextLine();
		String choiceOfBooking = scanner.nextLine();
		if ("B".equalsIgnoreCase(choiceOfBooking)) {
			SeatService.popResellList(booking);
			booking.setUser(loggedInUser);
			SeatService.putBookingList(booking);
			System.out.println("Your booking has been made");
		} else if ("N".equalsIgnoreCase(choiceOfBooking)) {
			System.out.println("Enter your desired price for the seat");
			Double desiredPrice = scanner.nextDouble();
			Bid bid = new Bid(loggedInUser.getUserDetails(), desiredPrice);
			List<Bid> resellBids = booking.getResellBids();
			if (resellBids == null) {
				resellBids = new ArrayList<>();
			}
			resellBids.add(bid);
			booking.setResellBids(resellBids);

			System.out.println("The seat's owner has been notified");
		} else {
			System.out.println("Wrong input");
		}
	}

	private void showResellBookings() {
		for (Booking booking : SeatService.getResellPostingsExceptUser(loggedInUser)) {
			System.out.println("Booking ID " + booking.getId() + ", Seat No. ("
					+ booking.getSeat().getRow() + "," + booking.getSeat().getColumn() + ") in Room of type "
					+ booking.getSeat().getRoom().getRoomType() + " at "
					+ booking.getSeat().getRoom().getBeginTimeStamp() + " for $" + booking.getPrice());
		}
	}

	private void bookANewSeat() {
		Room chosenRoom = chooseARoom();
		List<Integer> chosenSeat = chooseASeat(chosenRoom);
		int bookingId = SeatService.bookSeat(loggedInUser, chosenRoom, chosenSeat.get(0),
				chosenSeat.get(1));
		System.out.println("Your seat (" + chosenSeat.get(0) + "," + chosenSeat.get(1)
				+ ") has been booked. The Booking ID is " + bookingId);
	}

	private List<Integer> getRowAndColumnFromSeatNo(String seatNo) {
		int row = Integer.parseInt(seatNo.split(",")[0]);
		int column = Integer.parseInt(seatNo.split(",")[1]);
		return new ArrayList<>() {
			{
				add(row);
				add(column);
			}
		};

	}

	private List<Integer> chooseASeat(Room chosenRoom) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please choose a seat from the below list");
		SeatService.showSeatsByRoom(chosenRoom);
		String choiceOfSeat = scanner.nextLine();
		return getRowAndColumnFromSeatNo(choiceOfSeat);
	}

	private Room chooseARoom() {
		List<Room> eligibleRooms = RoomService.getRoomList().stream()
				.filter(r -> !checkIfOverlap(r.getBeginTimeStamp(), r.getEndTimeStamp()))
				.collect(Collectors.toList());

		Scanner scanner = new Scanner(System.in);
		System.out.println("Please choose a room from below and enter the id");
		for (Room room : eligibleRooms) {
			System.out.println("Room ID: " + room.getId() + ", of type " + room.getRoomType()
					+ " and regular price " + room.getRegularPrice() + ", posted by "
					+ room.getBusinessOwner().getUserDetails().getFirstName() + ", and available from "
					+ room.getBeginTimeStamp() + " to " + room.getEndTimeStamp());
		}
		int choiceOfRoom = scanner.nextInt();
		return RoomService.getRoomById(choiceOfRoom);
	}

	/**
	 * Verify if the timestamps follow the required rules
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	private Boolean checkIfOverlap(LocalDateTime beginTime, LocalDateTime endTime) {
		List<LocalDateTime> unavailabilityOfUser = getBookingTimeStampsOfLoggedInUser();
		for (LocalDateTime time : unavailabilityOfUser) {
			if ((time.isAfter(beginTime) && time.isBefore(endTime))
					|| beginTime.isBefore(LocalDateTime.now())) {
				return true;
			}
		}
		return false;
	}

	private List<LocalDateTime> getBookingTimeStampsOfLoggedInUser() {
		return SeatService.getBookingList().stream().filter(
				b -> b.getUser().getUserDetails().getEmail() == loggedInUser.getUserDetails().getEmail())
				.map(b -> b.getTimestamp()).collect(Collectors.toList());
	}
}
