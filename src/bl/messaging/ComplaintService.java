package bl.messaging;

import java.util.ArrayList;
import java.util.List;

import core.entity.Booking;
import core.entity.Complaint;
import core.entity.User;

public class ComplaintService {

	static List<Complaint> complaintList = new ArrayList<>();

	public static List<Complaint> getComplaintList() {
		return complaintList;
	}

	public static void putComplaintList(Complaint complaint) {
		complaintList.add(complaint);
	}

	/**
	 * Register a complaint against a booking
	 * @param user
	 * @param booking
	 * @param description
	 * @return
	 */
	public static Integer registerComplaint(User user, Booking booking, String description) {
		String title = "Complaint for Booking ID: " + booking.getId() + " posted by "
				+ booking.getSeat().getRoom().getBusinessOwner().getUserDetails().getFirstName();
		Complaint complaint = new Complaint(title, description, user);
		putComplaintList(complaint);
		return complaint.getId();
	}
}
