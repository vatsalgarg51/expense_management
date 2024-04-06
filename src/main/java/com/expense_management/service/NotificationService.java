package main.java.com.expense_management.service;

import main.java.com.expense_management.model.Expense;
import main.java.com.expense_management.model.User;

public class NotificationService {
	public void sentNotificationToUser(User user, Expense expense) {
        System.out.println("Notification sent to user");
    }
}
