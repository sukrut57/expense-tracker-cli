package com.backend.expensetrackercli.service;

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
import java.util.Objects;

@Service
public class FileService {
    private final String FILE_NAME = "expenses.json";

    @Value("${file.base.path}")
    private String FILE_PATH;

    private final ObjectMapper objectMapper;

    public FileService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.objectMapper.registerModule(new JavaTimeModule()); //register JavaTimeModule to support LocalDateTime

    }

    public List<Expense> retrieveExpenses() throws IOException {
        File file = new File(FILE_PATH + FILE_NAME);
        if(!file.exists() || file.length() ==0){
            return new ArrayList<>();
        }
        return objectMapper.readValue
                (Files.newBufferedReader(
                        Paths.get(FILE_PATH + FILE_NAME)),
                        new TypeReference<List<Expense>>() {
        });
    }

    public void saveExpense(Expense expense) throws IOException {
        new File(FILE_PATH).mkdirs(); // Ensure directory exists
        List<Expense> expenses = retrieveExpenses();
        expenses.add(expense);
        try(FileWriter fileWriter =new FileWriter(FILE_PATH + FILE_NAME)){
            objectMapper.writeValue(fileWriter,expenses);
        }

    }

    public void updateExpense(Expense expense) throws IOException {
        List<Expense> expenseList = retrieveExpenses();
        expenseList.removeIf(exp -> exp.getId() == expense.getId());
        expenseList.add(expense);
        try(FileWriter fileWriter = new FileWriter(FILE_PATH + FILE_NAME)){
            objectMapper.writeValue(fileWriter, expenseList);
        }
    }
}
