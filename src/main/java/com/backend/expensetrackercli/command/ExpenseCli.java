package com.backend.expensetrackercli.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import static java.lang.System.out;

@ShellComponent
public class ExpenseCli {

    @ShellMethod(key = "add", value = "Add an expense")
    public String addExpense(@ShellOption String description) {
        out.println(description);
        return "Expense added successfully";
    }

}
