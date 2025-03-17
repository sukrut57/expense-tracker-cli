package com.backend.expensetrackercli.model;

import java.time.LocalDateTime;

public class Expense {
    private static Long counter = 0L;
    private Long id;
    private String description;
    private Double amount;
    private Category category;
    private LocalDateTime expenseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }

    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
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

    public Expense() {
    }

    public Expense(String description, Double amount, Category category, LocalDateTime expenseDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = counter++;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.expenseDate = expenseDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }



}
