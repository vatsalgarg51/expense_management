package main.java.com.expense_management.model;

import lombok.*;

import java.util.*;

@Getter
public class ExpenseGroup {

    private Set<User> groupMembers = new HashSet<>();

    public ExpenseGroup() {
        this.expenseGroupId = UUID.randomUUID().toString();
        this.groupMembers = new HashSet<>();
        this.userContributions = new HashMap<>();
    }

    private String expenseGroupId;
    @Setter
    private Map<String, UserShare> userContributions;

}
