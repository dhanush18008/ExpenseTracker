package com.expenses.ExpenseTracker.controller;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.services.ExpenseCategoryJPAService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExpenseCategoryController {
    @Autowired
    ExpenseCategoryJPAService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<String> getAllCategories() {
        try{
        List<ExpenseCategory> expenseCategories = categoryService.getAllCategories();
        Map<String,List<ExpenseCategory>> hashmap=new HashMap<>();
        hashmap.put("AllCategories",expenseCategories);
        ObjectMapper mapper=new ObjectMapper();
        String jsonResponse=mapper.writeValueAsString(hashmap);
           // jsonResponse="AllCategories = "+jsonResponse;
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (JsonProcessingException e){
            return new ResponseEntity<>("Error occurred while processing JSON response.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<ExpenseCategory> getCategoryById(@PathVariable("categoryId") int categoryId) {
        ExpenseCategory category=categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
    @PostMapping("/categories")
    public ResponseEntity<ExpenseCategory> createCategory(@RequestBody ExpenseCategory category) {
        if (category.getCategoryName().length() > 30 || category.getCategoryBudget() >= Integer.MAX_VALUE) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        category.setDateOfCreation(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
        category.setLastUpdatedDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
        ExpenseCategory createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    @PutMapping("/categories/{categoryId}")
    public ResponseEntity<ExpenseCategory> updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestBody ExpenseCategory category) {
        if (category.getCategoryName().length() > 30 || category.getCategoryBudget() >= Integer.MAX_VALUE) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        ExpenseCategory updatedCategory = categoryService.updateCategory(categoryId, category);
        if (updatedCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    @DeleteMapping("/categories/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/categories/spend")
    public ResponseEntity<Object> spend(@RequestBody ExpenseCategory category) {
            ExpenseCategory spentCategory = categoryService.spend(category);
            return new ResponseEntity<>(spentCategory, HttpStatus.OK);
    }


    @GetMapping("/categories/amount")
    public ResponseEntity<Map<String, Integer>> amountRequired() {
        return new ResponseEntity<>(categoryService.amountRequired(), HttpStatus.OK);
    }

    @GetMapping("/categories/history")
    public ResponseEntity<ByteArrayResource> getExpenseHistoryFile() {
        try {

            byte[] fileContent = Files.readAllBytes(Paths.get(ExpenseCategoryJPAService.HISTORY_FILE_PATH));


            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("history", "expense_history.txt");
            headers.setContentLength(fileContent.length);


            ByteArrayResource resource = new ByteArrayResource(fileContent);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static final String HISTORY_FILE_PATH = "src/main/resources/expense_history.txt";

    @GetMapping("/categories/history/download")
    public ResponseEntity<ByteArrayResource> downloadExpenseHistoryFile() {
        try {
            // Read the contents of the history file
            byte[] fileContent = Files.readAllBytes(Paths.get(HISTORY_FILE_PATH));

            // Set headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("attachment", "expense_history.txt");
            headers.setContentLength(fileContent.length);

            // Return the history file as a response entity
            ByteArrayResource resource = new ByteArrayResource(fileContent);
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentLength(fileContent.length)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

}
