package core.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DateUtils {
	
	/**
	 * Takes date and time input from the user to create a Java object
	 * @param quesFormat To specify which date or time in the question
	 * @return
	 */
	public static LocalDateTime inputDateFromUser(String quesFormat) {
		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter the " + quesFormat + " Date in DD-MM-YYYY format");
		String inputDate = scanner.nextLine();
		System.out.println("Enter the " + quesFormat + " Time in HH:MM format");
		String inputTime = scanner.nextLine();

		String timestamp = inputDate + " " + inputTime;

		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		return LocalDateTime.parse(timestamp, dateFormatter);
	}
}
