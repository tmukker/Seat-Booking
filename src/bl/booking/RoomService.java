package bl.booking;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import core.entity.Room;
import core.entity.User;
import core.enums.RoomType;

public class RoomService {

	static List<Room> roomList = new ArrayList<>();

	public static List<Room> getRoomList() {
		return roomList;
	}

	public static void putRoomList(Room room) {
		roomList.add(room);
	}

	public static Room getRoomById(Integer id) {
		return getRoomList().stream().filter(r -> id == r.getId()).findFirst().orElse(null);
	}

	/**
	 * Books a room so that owner can make it available for booking
	 * @param loggedInUser
	 * @param roomType
	 * @param rows
	 * @param price
	 * @param beginTimeStamp
	 * @param endTimeStamp
	 * @return
	 */
	public static Integer bookRoom(User loggedInUser, RoomType roomType, int rows, double price,
			LocalDateTime beginTimeStamp, LocalDateTime endTimeStamp) {
		Room room = new Room(roomType, rows, price, loggedInUser, beginTimeStamp, endTimeStamp);
		putRoomList(room);
		return room.getId();
	}
}
