package com.backend.expensetrackercli.service;

import com.backend.expensetrackercli.model.Budget;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

import static java.lang.System.out;

@Service
public class BudgetService {

    private final FileService fileService;

    public BudgetService(FileService fileService) {
        this.fileService = fileService;
    }

    public void calculateRemainingBudget(String month, double amount, String action) throws IOException {
        Budget currentMonthBudget = fileService.retrieveBudgetByMonth(month);
        switch (action){
            case "add":
                currentMonthBudget.setRemainingBudget(currentMonthBudget.getRemainingBudget() + amount);
                fileService.updateBudget(currentMonthBudget);
                break;
            case "subtract":
                currentMonthBudget.setRemainingBudget(currentMonthBudget.getRemainingBudget() - amount);
                fileService.updateBudget(currentMonthBudget);
                break;
            default:
                out.println("Invalid action");
        }
        if(currentMonthBudget.getRemainingBudget() < 0){
            out.println("Warning: you have exceeded your budget for the month");
        }
    }

    public double getRemainingBudget() throws IOException {
        Budget currentMonthBudget = fileService.retrieveBudgetByMonth(LocalDateTime.now().getMonth().toString());
        return currentMonthBudget.getRemainingBudget();
    }

    public void createBudget(double amount) throws IOException{
        String month = LocalDateTime.now().getMonth().toString();
        Budget budget = new Budget(amount,amount, LocalDateTime.now(), LocalDateTime.now(), month);
        fileService.saveBudget(budget);

    }
}
