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
        out.println(description);
        String category = addExpenseCategory();
        out.println("\n");
        out.println("category selected: "+category);
        try{
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

    @ShellMethod(key="update", value="Update an expense")
    public String updateExpense(@ShellOption long id, @ShellOption String description, @ShellOption double amount){
        try{
            expenseService.updateExpense(id, description, amount);
            return "Expense updated successfully";
        }catch (Exception e){
            return "Unable to update expense";
        }
    }

    private List<String> printExpenses(List<Expense> expenses){
        List<String> expenseList = new ArrayList<>();
        expenses.forEach(expense ->
                expenseList.add(expense.getId() + " " + expense.getDescription() + " " + expense.getAmount() + " " + expense.getCategory() + " " +  expense.getExpenseDate().toLocalDate() + " " + expense.getCreatedAt().toLocalDate() + " " + expense.getUpdatedAt().toLocalDate()));
        return expenseList;
    }

}
