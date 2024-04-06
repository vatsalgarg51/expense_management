package main.java.com.expense_management.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import main.java.com.expense_management.exceptions.ExpenseNotExistException;
import main.java.com.expense_management.model.Contribution;
import main.java.com.expense_management.model.Expense;
import main.java.com.expense_management.model.ExpenseGroup;
import main.java.com.expense_management.model.ExpenseStatus;
import main.java.com.expense_management.model.UserShare;
import main.java.com.expense_management.repository.ExpenseRepository;
import main.java.com.expense_management.repository.UserRepository;


public class ExpenseService {
    private NotificationService notificationService = new NotificationService();

    public Expense createExpense(String title, String description, LocalDateTime expenseDate, double expenseAmount,
                                 String userId) {
        Expense expense = Expense.builder()
                .id(UUID.randomUUID().toString())
                .title(title)
                .description(description)
                .userId(userId)
                .expenseAmount(expenseAmount)
                .expenseDate(expenseDate)
                .expenseStatus(ExpenseStatus.CREATED)
                .expenseGroup(new ExpenseGroup())
                .build();
        ExpenseRepository.expenses.putIfAbsent(expense.getId(), expense);
        return expense;
    }

    public void addUsersToExpense(String expenseId, String emailId) throws
            ExpenseNotExistException{
        if (!ExpenseRepository.expenses.containsKey(expenseId)) {
            throw new
                    ExpenseNotExistException("This expense doesn't exist");
        }
        ExpenseRepository.expenses.get(expenseId)
                .getExpenseGroup().getGroupMembers()
                .add(UserRepository.users.get(emailId));

        if (notificationService != null) {
            notificationService.sentNotificationToUser(UserRepository.users.get(emailId),
                    ExpenseRepository.expenses.get(expenseId));
        }
    }

    public void assignExpenseShare(String expenseId, String emailId, double share)
            throws ExpenseNotExistException {
        if (!ExpenseRepository.expenses.containsKey(expenseId)) {
            throw new ExpenseNotExistException(String.format("Expense %s does not exists", expenseId));
        }
        Expense expense = ExpenseRepository.expenses.get(expenseId);
        expense.getExpenseGroup()
                .getUserContributions().putIfAbsent(emailId, new UserShare(emailId, share));

    }

    public void setExpenseStatus(String expenseId, ExpenseStatus expenseStatus) {
        Expense expense = ExpenseRepository.expenses.get(expenseId);
        expense.setExpenseStatus(expenseStatus);
    }

    public boolean isExpenseSettled(String expenseId) {
        Expense expense = ExpenseRepository.expenses.get(expenseId);
        ExpenseGroup expenseGroup = expense.getExpenseGroup();
        Map<String, UserShare> userContributions = expenseGroup.getUserContributions();

        double total = expense.getExpenseAmount();

        for (Map.Entry<String, UserShare> entry : userContributions.entrySet()) {
            UserShare userShare = entry.getValue();
            for (Contribution contribution : userShare.getContributions()) {
                total -= contribution.getContributionValue();
            }
        }
        if (total <= 1) {
            return true;
        }
        return false;
    }

}