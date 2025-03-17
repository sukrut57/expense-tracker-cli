package com.backend.expensetrackercli.service;

import com.backend.expensetrackercli.model.Budget;
import com.backend.expensetrackercli.model.Expense;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private final String EXPENSE_FILE_NAME = "expenses.json";
    private final String BUDGET_FILE_NAME = "budget.json";

    @Value("${file.base.path}")
    private String FILE_PATH;

    private final ObjectMapper objectMapper;

    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule()); //register JavaTimeModule to support LocalDateTime

    }

    public List<Expense> retrieveExpenses() throws IOException {
        File file = new File(FILE_PATH + EXPENSE_FILE_NAME);
        if(!file.exists() || file.length() ==0){
            return new ArrayList<>();
        }
        return objectMapper.readValue
                (Files.newBufferedReader(
                        Paths.get(FILE_PATH + EXPENSE_FILE_NAME)),
                        new TypeReference<List<Expense>>() {
        });
    }

    public void saveExpense(Expense expense) throws IOException {
        new File(FILE_PATH).mkdirs(); // Ensure directory exists
        List<Expense> expenses = retrieveExpenses();
        expenses.add(expense);
        try(FileWriter fileWriter =new FileWriter(FILE_PATH + EXPENSE_FILE_NAME)){
            objectMapper.writeValue(fileWriter,expenses);
        }

    }

    public void updateExpense(Expense expense) throws IOException {
        List<Expense> expenseList = retrieveExpenses();
        expenseList.removeIf(exp -> exp.getId() == expense.getId());
        expenseList.add(expense);
        try(FileWriter fileWriter = new FileWriter(FILE_PATH + EXPENSE_FILE_NAME)){
            objectMapper.writeValue(fileWriter, expenseList);
        }
    }

    public Expense deleteExpense(long id) throws IOException {
        List<Expense> expenses = retrieveExpenses();
        Expense expense = expenses.stream().filter(exp -> exp.getId() == id).findFirst()
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        expenses.remove(expense);
        try(FileWriter fileWriter = new FileWriter(FILE_PATH + EXPENSE_FILE_NAME)){
            objectMapper.writeValue(fileWriter, expenses);
        }
        return expense;
    }

    public void saveBudget(Budget budget) throws IOException{
        new File(FILE_PATH).mkdirs(); // Ensure directory exists
        try(FileWriter fileWriter = new FileWriter(FILE_PATH + BUDGET_FILE_NAME)){
            objectMapper.writeValue(fileWriter,budget);
        }
    }

    public Budget retrieveBudgetByMonth(String month) throws IOException{
        File file = new File(FILE_PATH + BUDGET_FILE_NAME);
        if(!file.exists() || file.length() ==0){
            return new Budget();
        }
        List<Budget> budgetList =  objectMapper.readValue
                (Files.newBufferedReader(
                        Paths.get(FILE_PATH + BUDGET_FILE_NAME)),
                        new TypeReference<List<Budget>>() {
        });
        return budgetList.stream().filter(budget -> budget.getMonth().equalsIgnoreCase(month)).findFirst().orElse(null);
    }

    public void updateBudget(Budget budget) throws IOException {

        List<Budget> budgetList = objectMapper.readValue
                (Files.newBufferedReader(
                        Paths.get(FILE_PATH + BUDGET_FILE_NAME)),
                        new TypeReference<List<Budget>>() {
                });

        budgetList.removeIf(budget1 -> budget1.getMonth().equalsIgnoreCase(budget.getMonth()));
        Budget budgetByMonth = retrieveBudgetByMonth(budget.getMonth());
        budgetByMonth.setMonthlyBudget(budget.getMonthlyBudget());
        budgetByMonth.setRemainingBudget(budget.getRemainingBudget());
        budgetByMonth.setUpdatedAt(budget.getUpdatedAt());
        budgetList.add(budgetByMonth);

        try(FileWriter fileWriter = new FileWriter(FILE_PATH + BUDGET_FILE_NAME)){
            objectMapper.writeValue(fileWriter, budgetList);
        }
    }
}
