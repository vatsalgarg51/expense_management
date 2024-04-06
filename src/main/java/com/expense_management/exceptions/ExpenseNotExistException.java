package main.java.com.expense_management.exceptions;

public class ExpenseNotExistException extends Exception {

    public ExpenseNotExistException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return this.getMessage();
    }
}