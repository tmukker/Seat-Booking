package core.entity;

public class Seat {

	private Integer row;

	private Integer column;

	private Room room;

	public Seat(Integer row, Integer column, Room room) {
		super();
		this.row = row;
		this.column = column;
		this.room = room;
	}

	/**
	 * @return the row
	 */
	public Integer getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(Integer row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public Integer getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(Integer column) {
		this.column = column;
	}

	/**
	 * @return the room
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * @param room the room to set
	 */
	public void setRoom(Room room) {
		this.room = room;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Seat [row=").append(row).append(", column=").append(column).append(", room=")
				.append(room).append("]");
		return builder.toString();
	}
}
