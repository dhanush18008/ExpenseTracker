package com.expenses.ExpenseTracker.controller;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.services.ExpenseCategoryJPAService;
import jakarta.validation.Valid;
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
import java.util.List;
import java.util.Map;

@RestController
public class ExpenseCategoryController {
    @Autowired
    ExpenseCategoryJPAService categoryService;
    @GetMapping("ExpenseTracker/v1.0/categories/all")
    public ResponseEntity<List<ExpenseCategory>> getAllCategories() {
//        try{
        List<ExpenseCategory> expenseCategories = categoryService.getAllCategories();
//        Map<String,List<ExpenseCategory>> hashmap=new HashMap<>();
//        hashmap.put("AllCategories",expenseCategories);
//        ObjectMapper mapper=new ObjectMapper();
//        String jsonResponse=mapper.writeValueAsString(hashmap);
           // jsonResponse="AllCategories = "+jsonResponse;
            return new ResponseEntity<>(expenseCategories, HttpStatus.OK);
//        }catch (JsonProcessingException e){
//            return new ResponseEntity<>("Error occurred while processing JSON response.", HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @GetMapping("ExpenseTracker/v1.0/categories/{categoryId}")
    public ResponseEntity<ExpenseCategory> getCategoryById(@PathVariable("categoryId") int categoryId) {
        ExpenseCategory category=categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category,HttpStatus.OK);
    }
    @PostMapping("ExpenseTracker/v1.0/categories/create")
    public ResponseEntity<ExpenseCategory> createCategory(@RequestBody @Valid ExpenseCategory category) {
        category.setDateOfCreation(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
        category.setLastUpdatedDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());
        ExpenseCategory createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }
    @PutMapping("ExpenseTracker/v1.0/categories/update/{categoryId}")
    public ResponseEntity<ExpenseCategory> updateCategory(
            @PathVariable("categoryId") int categoryId,
            @RequestBody @Valid ExpenseCategory category) {
        if (category.getCategoryName().length() > 30 || category.getCategoryBudget() >= Integer.MAX_VALUE) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        ExpenseCategory updatedCategory = categoryService.updateCategory(categoryId, category);
        if (updatedCategory == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }
    @DeleteMapping("ExpenseTracker/v1.0/categories/delete/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") int categoryId) {
        ExpenseCategory cc=categoryService.getCategoryById(categoryId);
        categoryService.deleteCategory(categoryId);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("ExpenseTracker/v1.0/categories/CategoryAmount")
    public ResponseEntity<Map<String, Integer>> amountRequired() {
        return new ResponseEntity<>(categoryService.amountRequired(), HttpStatus.OK);
    }
    @PostMapping("ExpenseTracker/v1.0/categories/spend")
    public ResponseEntity<Object> spend(@RequestBody ExpenseCategory category) {
            ExpenseCategory spentCategory = categoryService.spend(category);
            return new ResponseEntity<>(spentCategory, HttpStatus.OK);
    }




    @GetMapping("ExpenseTracker/v1.0/categories/history")
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

}
