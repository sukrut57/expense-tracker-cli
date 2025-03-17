package com.backend.expensetrackercli.model;

import java.time.LocalDateTime;

public class Budget {
    private static long counter = 0;
    private long budgetId;
    private double monthlyBudget;
    private double remainingBudget;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String month;

    public double getMonthlyBudget() {
        return monthlyBudget;
    }

    public void setMonthlyBudget(double monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    public long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(long budgetId) {
        this.budgetId = budgetId;
    }

    public double getRemainingBudget() {
        return remainingBudget;
    }

    public void setRemainingBudget(double remainingBudget) {
        this.remainingBudget = remainingBudget;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Budget(){

    }

    public Budget(double monthlyBudget, double remainingBudget, LocalDateTime createdAt, LocalDateTime updatedAt, String month) {
        this.budgetId = counter++;
        this.monthlyBudget = monthlyBudget;
        this.remainingBudget = remainingBudget;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.month = month;
    }


}
