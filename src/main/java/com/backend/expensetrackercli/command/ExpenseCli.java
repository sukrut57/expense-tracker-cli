package com.backend.expensetrackercli.command;

import com.backend.expensetrackercli.model.Category;
import com.backend.expensetrackercli.model.Expense;
import com.backend.expensetrackercli.service.ExpenseService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.out;

@ShellComponent
public class ExpenseCli {

    private final ExpenseService expenseService;

    public ExpenseCli(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @ShellMethod(key = "add", value = "Add an expense")
    public String addExpense(@ShellOption String description, @ShellOption double amount) {
        try{
            if(description.trim().isEmpty() || amount == 0){
                return "Description and amount cannot be empty";
            }
            String category = addExpenseCategory();
            out.println("\n");
            out.println("category selected: "+category);
            expenseService.addExpense(description, amount, category);
            return "Expense added successfully";
        }catch (Exception e){
            return "Unable to add expense";
        }
    }

    private String addExpenseCategory(){
        Scanner scanner = new Scanner(System.in);
        out.println("Select a category : ");
        for(int i=0;i<Category.values().length;i++){
            out.println(i + ". " + Category.values()[i]);
        }
        out.println("\n");

        out.print("Enter your choice: ");
        int option = scanner.nextInt();

        while(option<0 || option>=Category.values().length){
            out.println("Invalid option. Please select a valid option");
            out.print("Enter your choice: ");
            option = scanner.nextInt();
        }
        return Category.values()[option].toString();
    }

    @ShellMethod(key = "list", value = "List all expenses")
    public List<String> listExpenses(){
        try{
            return printExpenses(expenseService.getAllExpenses());
        }
        catch (Exception e){
            return List.of("Unable to list expenses");
        }
    }

    @ShellMethod(key = "summary", value = "Get summary of expenses")
    public String getSummary(){
        try{
            double summary = expenseService.getSummary();
            return "Total expenses: " + summary;
        }
        catch (Exception e){
            return "unable to get summary";
        }
    }

    @ShellMethod(key="monthly-summary", value="Get monthly summary of expenses")
    public String getMonthlySummary(@ShellOption String month){
        try{
            double summary = expenseService.getMonthlySummary(month);
            return "Total expenses for " + month + " : " + summary;
        }
        catch (Exception e){
            return "unable to get monthly summary";
        }
    }

    @ShellMethod(key="update", value="Update an expense")
    public String updateExpense(@ShellOption long id, @ShellOption String description, @ShellOption double amount){
        try{
            if(description.isEmpty() || amount == 0){
                return "Description and amount cannot be empty";
            }
            expenseService.updateExpense(id, description, amount);
            expenseService.updateExpense(id, description, amount);
            return "Expense updated successfully";
        }catch (Exception e){
            return "Unable to update expense";
        }
    }

    @ShellMethod(key="delete", value="Delete an expense")
    public String deleteExpense(@ShellOption long id){
        try{
            expenseService.deleteExpense(id);
            return "Expense deleted successfully";
        }catch (Exception e){
            return "Unable to delete expense";
        }
    }

    private List<String> printExpenses(List<Expense> expenses){
        List<String> expenseList = new ArrayList<>();
        expenses.forEach(expense ->
                expenseList.add(expense.getId() + " " + expense.getDescription() + " " + expense.getAmount() + " " + expense.getCategory() + " " +  expense.getExpenseDate().toLocalDate() + " " + expense.getCreatedAt().toLocalDate() + " " + expense.getUpdatedAt().toLocalDate()));
        return expenseList;
    }

}
