package main.java.com.expense_management.service;

import main.java.com.expense_management.exceptions.ContributionExceedException;
import main.java.com.expense_management.exceptions.ExpenseSettledException;
import main.java.com.expense_management.exceptions.InvalidExpenseStateException;
import main.java.com.expense_management.model.Contribution;
import main.java.com.expense_management.model.Expense;
import main.java.com.expense_management.model.ExpenseGroup;
import main.java.com.expense_management.model.ExpenseStatus;
import main.java.com.expense_management.model.User;
import main.java.com.expense_management.model.UserShare;
import main.java.com.expense_management.repository.ExpenseRepository;
import main.java.com.expense_management.repository.UserRepository;

public class UserService {
	public User createUser(String emailId, String name, String phoneNumber) {
        User user = new User(emailId, name, phoneNumber);
        UserRepository.users.putIfAbsent(emailId, user);
        return user;
    }

    public void contributeToExpense(String expenseId, String emailId,
                                    Contribution contribution)
            throws InvalidExpenseStateException, ExpenseSettledException, ContributionExceedException {
        Expense expense = ExpenseRepository.expenses.get(expenseId);
        ExpenseGroup expenseGroup = expense.getExpenseGroup();
        if (expense.getExpenseStatus() == ExpenseStatus.CREATED) {
            throw new InvalidExpenseStateException("Invalid expense State");
        }
        if (expense.getExpenseStatus() == ExpenseStatus.SETTLED) {
            throw new ExpenseSettledException("Expense is already settled.");
        }
        UserShare userShare = expenseGroup.getUserContributions().get(emailId);
        if (contribution.getContributionValue() > userShare.getShare()) {
            throw new ContributionExceedException(
                    String.format("User %s contribution %d exceeded the share %d",
                            emailId, contribution.getContributionValue(), userShare.getShare()));
        }
        userShare.getContributions().add(contribution);
    }

    public void contributeToExpense(String expenseId, String emailId, String toEmailId,
                                    Contribution contribution)
            throws InvalidExpenseStateException, ExpenseSettledException, ContributionExceedException {
        Expense expense = ExpenseRepository.expenses.get(expenseId);
        ExpenseGroup expenseGroup = expense.getExpenseGroup();
        if (expense.getExpenseStatus() == ExpenseStatus.CREATED) {
            throw new InvalidExpenseStateException("Invalid expense State");
        }
        if (expense.getExpenseStatus() == ExpenseStatus.SETTLED) {
            throw new ExpenseSettledException("Expense is already settled.");
        }
        UserShare userShare = expenseGroup.getUserContributions().get(emailId);
        if (contribution.getContributionValue() > userShare.getShare()) {
            throw new ContributionExceedException(
                    String.format("User %s contribution %d exceeded the share %d",
                            emailId, contribution.getContributionValue(), userShare.getShare()));
        }
        userShare.getContributions().add(contribution);
    }
}
