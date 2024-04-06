package main.java.com.expense_management.exceptions;

public class InvalidExpenseStateException extends Exception {

    public InvalidExpenseStateException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}