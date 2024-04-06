package main.java.com.expense_management.repository;

import lombok.*;
import main.java.com.expense_management.model.User;

import java.util.*;

@Getter
@Setter
public class UserRepository {
    public static Map<String, User> users = new HashMap<>();
}
