package com.backend.expensetrackercli.service;

import com.backend.expensetrackercli.model.Category;
import com.backend.expensetrackercli.model.Expense;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static java.lang.System.out;

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

    public void deleteExpense(long id) throws IOException {
        fileService.deleteExpense(id);
    }

    public double getSummary() throws IOException {
        List<Expense> expenses = fileService.retrieveExpenses();
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        return total;
    }

    public double getMonthlySummary(String month) throws IOException {
        List<Expense> expenses = fileService.retrieveExpenses();
        int monthlySummary = 0;
        for(Expense expense:expenses){
            if(expense.getExpenseDate().getMonth().toString().equalsIgnoreCase(month.toUpperCase())){
                monthlySummary += expense.getAmount();
            }
        }
        return monthlySummary;
    }
}
