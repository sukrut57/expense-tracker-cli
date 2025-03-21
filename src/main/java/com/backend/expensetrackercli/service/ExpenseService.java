package com.backend.expensetrackercli.service;

import com.backend.expensetrackercli.model.Budget;
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
    private final BudgetService budgetService;

    public ExpenseService(FileService fileService, BudgetService budgetService) {
        this.fileService = fileService;
        this.budgetService = budgetService;
    }

    public void addExpense(String description, double amount, String category) throws IOException{
        Budget currentMonthBudget = fileService.retrieveBudgetByMonth(LocalDateTime.now().getMonth().toString());
        LocalDateTime expenseDate = LocalDateTime.now();
        Expense expense = new Expense(description, amount, Category.valueOf(category), expenseDate, expenseDate, expenseDate, currentMonthBudget.getBudgetId());
        fileService.saveExpense(expense);

        //update the budget for the current month after adding the expense
        budgetService.calculateRemainingBudget(expenseDate.getMonth().toString(), amount, "subtract");
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

        double difference = expense.getAmount() - amount;
        expense.setDescription(description);
        expense.setAmount(amount);
        expense.setUpdatedAt(LocalDateTime.now());
        fileService.updateExpense(expense);

        //update the budget for the current month after adding the expense
        budgetService.calculateRemainingBudget(expense.getExpenseDate().getMonth().toString(), difference, "add");

    }

    public void deleteExpense(long id) throws IOException {
        Expense expense = fileService.deleteExpense(id);
        //update the budget for the current month after adding the expense
        budgetService.calculateRemainingBudget(expense.getExpenseDate().getMonth().toString(), expense.getAmount(), "add");
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


    public double getCategorySummary(String category) throws IOException {
        List<Expense> expenses = fileService.retrieveExpenses();
        List<Expense> categoryExpenses = expenses.stream().filter(expense -> expense.getCategory().toString().equalsIgnoreCase(category)).toList();
        double total = 0;
        if(categoryExpenses.isEmpty()){
            out.println("No expenses found for the category");
            return total;
        }
        for(Expense expense:categoryExpenses){
            total += expense.getAmount();
        }
        return total;
    }
}
