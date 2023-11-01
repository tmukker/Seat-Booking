package bl.messaging;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import core.entity.Message;
import core.entity.User;

public class MessagingService {
	static List<Message> messageList = new ArrayList<>();

	public static List<Message> getMessageList() {
		return messageList;
	}

	public static void putMessageList(Message message) {
		messageList.add(message);
	}

	/**
	 * Send a message to another user
	 * @param user1
	 * @param user2
	 * @param text
	 */
	public static void sendMessage(User user1, User user2, String text) {
		String title = "Message from " + user1.getUserDetails().getEmail();
		Message message = new Message(title, text, user1, user2);
		putMessageList(message);
	}

	/*
	 * See all the messages received
	 */
	public static void displayMessages(User user) {
		List<Message> usersMessages = getMessageList().stream()
				.filter(m -> m.getUser2().getUserDetails().getEmail() == user.getUserDetails().getEmail())
				.collect(Collectors.toList());
		for (Message message : usersMessages) {
			System.out.println(message.getTitle() + ", Message Body: " + message.getDesc());
		}
	}
}
