package com.backend.expensetrackercli.service;

import com.backend.expensetrackercli.model.Category;
import com.backend.expensetrackercli.model.Expense;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseService {

    private final FileService fileService;

    public ExpenseService(FileService fileService) {
        this.fileService = fileService;
    }

    public void addExpense(String description, double amount, String category) throws IOException{
        LocalDateTime expenseDate = LocalDateTime.now();
        Expense expense = new Expense(description, amount, Category.valueOf(category), expenseDate, expenseDate, expenseDate);
        fileService.saveExpense(expense);
    }

    public List<Expense> getAllExpenses() throws IOException {
        return fileService.retrieveExpenses();
    }

    public void updateExpense(long id, String description, double amount) throws IOException {
        List<Expense> expenseList = fileService.retrieveExpenses();
        Expense expense =expenseList.stream().filter(exp -> exp.getId() == id).findFirst().orElse(null);
        if(expense == null){
            throw new RuntimeException("Expense not found");
        }
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setUpdatedAt(LocalDateTime.now());
        fileService.updateExpense(expense);
    }
}
