package com.expenses.ExpenseTracker.controller;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.services.ExpenseCategoryJPAService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ExpenseCategoryController {
    @Autowired
    ExpenseCategoryJPAService categoryService;
    @GetMapping("/categories")
    public ResponseEntity<String> getAllCategories() {
        List<ExpenseCategory> expenseCategories = categoryService.getAllCategories();
        ObjectMapper mapper=new ObjectMapper();
        String jsonResponse;
        try {
            jsonResponse=mapper.writeValueAsString(expenseCategories);
            jsonResponse="AllCategories = "+jsonResponse;
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/categories/{categoryId}")
    public ExpenseCategory getCategoryById(@PathVariable("categoryId") int categoryId) {
        return categoryService.getCategoryById(categoryId);
    }


    @PostMapping("/categories")
    public ExpenseCategory createCategory(@RequestBody ExpenseCategory category) {
        return categoryService.createCategory(category);
    }
    @PutMapping("/categories/{categoryId}")
    public ExpenseCategory updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestBody ExpenseCategory category) {
        return categoryService.updateCategory(categoryId, category);
    }
    @DeleteMapping("/categories/{categoryId}")
    public void deleteCategory(@PathVariable("categoryId") int categoryId) {
        categoryService.deleteCategory(categoryId);
    }
    @PostMapping("/categories/spend/{categoryId}")
    public ExpenseCategory spend(@PathVariable("categoryId")int categoryId,@RequestBody ExpenseCategory category){
        return categoryService.spend(categoryId,category);
    }
        @GetMapping("/categories/amount")
    public ResponseEntity<Map<String, Integer>> amountRequired() {
        List<ExpenseCategory> expenseCategories = categoryService.getAllCategories();
        Map<String, Integer> categoryBudgets = new HashMap<>();
        int sum=0;

        for (ExpenseCategory category : expenseCategories) {
            categoryBudgets.put(category.getCategoryName(), category.getCategoryBudget());
            sum+=category.getCategoryBudget();
        }
        categoryBudgets.put("Total : ",sum);

        return new ResponseEntity<>(categoryBudgets, HttpStatus.OK);
    }

    @GetMapping("/categories/history")
    public ResponseEntity<ByteArrayResource> getExpenseHistoryFile() {
        try {
            // Read the contents of the history file
            byte[] fileContent = Files.readAllBytes(Paths.get(ExpenseCategoryJPAService.HISTORY_FILE_PATH));

            // Set headers for the response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("history", "expense_history.txt");
            headers.setContentLength(fileContent.length);

            // Return the history file as a response entity
            ByteArrayResource resource = new ByteArrayResource(fileContent);
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
