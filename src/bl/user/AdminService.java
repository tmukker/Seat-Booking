package bl.user;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import bl.messaging.ComplaintService;
import core.entity.Complaint;
import core.entity.User;
import core.entity.UserDetails;
import core.enums.Role;

public class AdminService extends UserService {

	@Override
	public void menu() {
		Boolean isLoggedIn = true;
		while (isLoggedIn) {
			System.out.println("✦•······················•✦•······················•✦");
			System.out.println("Please choose an option from the below menu");

			System.out.println("1. Register a business owner");
			System.out.println("2. Register a client");
			System.out.println("3. Display all business owners");
			System.out.println("4. Display all clients");
			System.out.println("5. Check Complaints");
			System.out.println("6. Logout");

			Scanner scanner = new Scanner(System.in);
			System.out.println("Press an option: ");
			Integer option = scanner.nextInt();
//			scanner.nextLine();
			switch (option) {
			case 1:
				registerUser(Role.OWNER);
				break;
			case 2:
				registerUser(Role.CLIENT);
				break;
			case 3:
				displayUsersByRole(Role.OWNER);
				break;
			case 4:
				displayUsersByRole(Role.CLIENT);
				break;
			case 5:
				displayComplaints();
				break;
			case 6:
				isLoggedIn = false;
				logout();
			default:
				System.out.println("Wrong option!");
				break;
			}
		}

	}

	private void displayComplaints() {
		System.out.println("Following complaints have been registered");
		for (Complaint complaint : ComplaintService.getComplaintList()) {
			System.out.println("Complaint ID: " + complaint.getId() + " by "
					+ complaint.getUser1().getUserDetails().getFirstName() + ", Title: "
					+ complaint.getTitle() + " and Description: " + complaint.getDesc());
		}
	}

	/**
	 * Filters the users according to the roles and displays the necessary information
	 * @param role
	 */
	private void displayUsersByRole(Role role) {
		List<User> allUsers = getUserList();
		List<User> filteredUsersByRole = allUsers.stream().filter(user -> user.getRole().equals(role))
				.collect(Collectors.toList());

		for (User user : filteredUsersByRole) {
			System.out.println("Name: " + user.getUserDetails().getFirstName() + " "
					+ user.getUserDetails().getLastName());
		}
	}

	private void registerUser(Role role) {
		Scanner scanner = new Scanner(System.in);
		String firstName, lastName, email, password;
		System.out.println("Please enter details of the user");
		System.out.println("First Name: ");
		firstName = scanner.nextLine();
		System.out.println("Last Name: ");
		lastName = scanner.nextLine();
		System.out.println("Email: ");
		email = scanner.nextLine();
		System.out.println("Password: ");
		password = scanner.nextLine();
		UserDetails userDetails = new UserDetails(firstName, lastName, email, password);
		putUserList(new User(userDetails, role));
		System.out.println(firstName + " has been registered successfully");
//		scanner.close();
	}
}
