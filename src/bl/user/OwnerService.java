package bl.user;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import bl.booking.RoomService;
import bl.booking.SeatService;
import core.entity.Room;
import core.enums.RoomType;
import core.utils.DateUtils;

public class OwnerService extends UserService {

	@Override
	public void menu() {
		Boolean isLoggedIn = true;
		while (isLoggedIn) {
			System.out.println("✦•······················•✦•······················•✦");
			System.out.println("Please choose an option from the below menu");

			System.out.println("1. Register a room");
			System.out.println("2. Display my registered rooms");
			System.out.println("3. Display seats and their availability");
			System.out.println("4. Logout");

			Scanner scanner = new Scanner(System.in);
			System.out.println("Press an option: ");
			Integer option = scanner.nextInt();
//			scanner.nextLine();
			switch (option) {
			case 1:
				int type, rows, price;
				LocalDateTime beginTimeStamp, endTimeStamp;

				System.out.println("Please choose the type of room and enter the respective number");
				for (int i = 0; i < RoomType.values().length; i++) {
					System.out.println((i + 1) + ". " + RoomType.values()[i]);
				}
				type = scanner.nextInt();
				type--;
				System.out.println("Enter the number of rows in the rooms");
				rows = scanner.nextInt();
				System.out.println("Enter the price of a regular seat");
				price = scanner.nextInt();
				beginTimeStamp = DateUtils.inputDateFromUser("Beginning");
				endTimeStamp = DateUtils.inputDateFromUser("End");
				long minutes = ChronoUnit.MINUTES.between(beginTimeStamp, endTimeStamp);
				if (minutes < 30) {
					System.out.println("The time difference must be atleast 30 minutes");
					continue;
				}
				Integer roomId = RoomService.bookRoom(loggedInUser, RoomType.values()[type], rows, price,
						beginTimeStamp, endTimeStamp);
				System.out.println("Your room with id " + roomId + " has been booked");
				break;
			case 2:
				List<Room> usersRooms = RoomService.getRoomList().stream().filter(r -> r.getBusinessOwner()
						.getUserDetails().getEmail().equals(loggedInUser.getUserDetails().getEmail()))
						.collect(Collectors.toList());

				for (Room room : usersRooms) {
					System.out.println("Room ID: " + room.getId() + ", of type " + room.getRoomType()
							+ " and size " + room.getSize());
				}
				break;
			case 3:
				System.out.println("Enter the room id to proceed");
				roomId = scanner.nextInt();
				SeatService.showSeatsByRoom(RoomService.getRoomById(roomId));
				break;
			case 4:
				isLoggedIn = false;
				logout();
				break;
			default:
				System.out.println("Wrong option!");
				break;
			}
		}
	}
}
