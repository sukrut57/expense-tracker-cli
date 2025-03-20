package com.backend.expensetrackercli.command;

import com.backend.expensetrackercli.service.BudgetService;
import com.backend.expensetrackercli.service.ExpenseService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class BudgetCli {

    private final BudgetService budgetService;

    public BudgetCli(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @ShellMethod(key="remaining-budget", value="Set budget for the month")
    public String remainingBudget(){
        try{
            double remainingBudget = budgetService.getRemainingBudget();
            return "Remaining budget: " + remainingBudget;
        }catch (Exception e){
            return "Unable to set budget";
        }
    }

    @ShellMethod(key="set-budget", value="Set budget for the month")
    public String createBudget(double amount){
        try{
            budgetService.createBudget(amount);
            return "Budget set successfully";
        }
        catch (Exception e){
            return "Unable to set budget";
        }
    }

}
