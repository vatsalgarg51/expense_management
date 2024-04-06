package main.java.com.expense_management.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class Contribution {
    private String contributionId;
    private double contributionValue;
    private String transactionId;
    private LocalDateTime contributionDate;
    private String transactionDescription;
}