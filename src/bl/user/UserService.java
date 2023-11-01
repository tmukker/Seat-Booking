package bl.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import core.entity.User;
import core.entity.UserDetails;
import core.enums.Role;

public abstract class UserService implements IUserService {

	public abstract void menu();

	// hard coded few users, admin can register more users
	static List<User> userList = new ArrayList<>(Arrays.asList(//
			new User(new UserDetails("Admin", "User", "admin@soen.com", "admin"), Role.ADMIN), //
			new User(new UserDetails("O1", "User", "bowner1@soen.com", "o1"), Role.OWNER), //
			new User(new UserDetails("O2", "User", "bowner2@soen.com", "o2"), Role.CLIENT), //
			new User(new UserDetails("C1", "User", "client1@soen.com", "c1"), Role.CLIENT), //
			new User(new UserDetails("C2", "User", "client2@soen.com", "c2"), Role.CLIENT), //
			new User(new UserDetails("C3", "User", "client3@soen.com", "c3"), Role.CLIENT)));

	static User loggedInUser = null;

	public static List<User> getUserList() {
		return userList;
	}

	public static void putUserList(User user) {
		userList.add(user);
	}

	/**
	 * The entry point and the main menu of the application
	 */
	public static void login() {
		while (true) {
			System.out.println("✦•······················•✦•······················•✦");
			System.out.println("Please login with your registered credentials");

			Scanner scanner = new Scanner(System.in);

			System.out.println("Email Id: ");
			String email = scanner.nextLine();
			System.out.println("Password: ");
			String password = scanner.nextLine();

			User lgInUser = getUserList().stream()
					.filter(user -> user.getUserDetails().getEmail().equals(email)
							&& user.getUserDetails().getPassword().equals(password))
					.findFirst().orElse(null);

			if (lgInUser == null) {
				System.out.println("Sorry, you have entered wrong credentials!");
				continue;
			}

			loggedInUser = lgInUser;

			System.out.println("Welcome " + lgInUser.getUserDetails().getFirstName());

			try {
				if (lgInUser.getRole().equals(Role.ADMIN)) {
					AdminService adminService = new AdminService();
					adminService.menu();
				} else if (lgInUser.getRole().equals(Role.OWNER)) {
					OwnerService ownerService = new OwnerService();
					ownerService.menu();
				} else if (lgInUser.getRole().equals(Role.CLIENT)) {
					ClientService clientService = new ClientService();
					clientService.menu();
				} else {
					System.out.println("ERROR");
				}
			} catch (Exception e) {
				System.out.println("Please retry with correct input format");
			}

		}
	}

	/**
	 * Logs the user out of the application
	 */
	protected static void logout() {
		System.out.println("You have been successfully logged out");
		login();
	}

	/**
	 * Find the user that matches the email
	 * 
	 * @param email email of user
	 */
	protected static User getUserByEmail(String email) {
		return userList.stream().filter(u -> email.equalsIgnoreCase(u.getUserDetails().getEmail()))
				.findFirst().orElse(null);
	}
}
