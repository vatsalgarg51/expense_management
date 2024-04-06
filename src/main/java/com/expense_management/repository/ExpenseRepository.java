package main.java.com.expense_management.repository;

import lombok.*;
import main.java.com.expense_management.model.Expense;

import java.util.*;

@Getter
@Setter
public class ExpenseRepository {
    public static Map<String, Expense> expenses = new HashMap<>();
}