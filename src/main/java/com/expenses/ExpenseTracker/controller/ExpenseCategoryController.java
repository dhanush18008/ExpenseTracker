package com.expenses.ExpenseTracker.controller;
import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.services.ExpenseCategoryH2Service;
import com.expenses.ExpenseTracker.services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
public class ExpenseCategoryController {
    @Autowired
    ExpenseCategoryH2Service categoryService;
    @GetMapping("/categories")
    public List<ExpenseCategory> getAllCategories() {
        return categoryService.getAllCategories();
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

//    @PutMapping("/categories/{categoryId}")
//    public ExpenseCategory spend(@PathVariable("categoryId") int categoryId,
//                                 @RequestBody ExpenseCategory category){
//        return categoryService.spend()
//    }

}
