package com.expenses.ExpenseTracker.services;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryJPARepository;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryRepository;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class ExpenseCategoryJPAService implements ExpenseCategoryRepository {
    @Autowired
    private ExpenseCategoryJPARepository expenseCategoryJPARepository;
    @Override
    public ArrayList<ExpenseCategory> getAllCategories() {
        return new ArrayList<>(expenseCategoryJPARepository.findAll());
    }
    public ArrayList<ExpenseCategory> getBooks() {
        return new ArrayList<>(expenseCategoryJPARepository.findAll());
    }

    @Override
    public ExpenseCategory getCategoryById(int categoryId) {
        try{
            ExpenseCategory expenseCategory=expenseCategoryJPARepository.findById(categoryId).get();
            return expenseCategory;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ExpenseCategory createCategory(ExpenseCategory category) {
        expenseCategoryJPARepository.save(category);
        return category;
    }
    @Override
    public ExpenseCategory updateCategory(int categoryId, ExpenseCategory category) {
        try {
            ExpenseCategory newExpenseCategory=expenseCategoryJPARepository.findById(categoryId).get();
            if(category.getCategoryName()!=null){
                newExpenseCategory.setCategoryName(category.getCategoryName());
            }
            if(category.getCategoryBudget()!=0){
                newExpenseCategory.setCategoryBudget(category.getCategoryBudget());
            }
            if(category.getDate()!=null){
                newExpenseCategory.setDate(category.getDate());
            }
            expenseCategoryJPARepository.save(newExpenseCategory);
            return newExpenseCategory;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteCategory(int categoryId) {
        try {
            expenseCategoryJPARepository.deleteById(categoryId);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }
}
