package com.expenses.ExpenseTracker.services;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryJPARepository;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class ExpenseCategoryJPAService implements ExpenseCategoryRepository {
    @Autowired
    private ExpenseCategoryJPARepository expenseCategoryJPARepository;
    public static final String HISTORY_FILE_PATH = "C:\\Users\\Admin\\Downloads\\ExpenseTracker\\src\\main\\resources\\expense_history.txt";
    @Override
    public ArrayList<ExpenseCategory> getAllCategories() {
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
        if(category.getCategoryName().length()>30 || category.getCategoryBudget() >= 2147483647)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        category.setDateOfCreation(LocalDate.now().toString());
        category.setLastUpdatedDate(LocalDate.now().toString());
        expenseCategoryJPARepository.save(category);
        return category;
    }
    @Override
    public ExpenseCategory updateCategory(int categoryId, ExpenseCategory category) {
        try {
            ExpenseCategory newExpenseCategory=expenseCategoryJPARepository.findById(categoryId).get();
            if(category.getCategoryName().length()>30 || category.getCategoryBudget()>=2147483647)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
            if(category.getCategoryName()!=null){
                newExpenseCategory.setCategoryName(category.getCategoryName());
            }
            if(category.getCategoryBudget()!=0){
                newExpenseCategory.setCategoryBudget(category.getCategoryBudget());
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

    @Override
    public ExpenseCategory spend(int categoryId, ExpenseCategory category) {
        try {
            ExpenseCategory expenseCategory=expenseCategoryJPARepository.findById(categoryId).get();
            if(expenseCategory.getCategoryBudget()==0 || expenseCategory.getCategoryBudget()<category.getCategoryBudget()){
                throw new ResponseStatusException(HttpStatus.NOT_EXTENDED);
            }

            expenseCategory.setLastUpdatedDate(LocalDate.now().toString());


            if(category.getCategoryBudget()!=0) {
                expenseCategory.setCategoryBudget(expenseCategory.getCategoryBudget() - category.getCategoryBudget());
            }
            logExpenseUpdateToHistory(category,expenseCategory.getCategoryBudget(),expenseCategory.getCategoryName());
            expenseCategoryJPARepository.save(expenseCategory);
            return expenseCategory;
        }catch (Exception e){
            ExpenseCategory expenseCategory=expenseCategoryJPARepository.findById(categoryId).get();
            if(expenseCategory.getCategoryBudget()==0 || expenseCategory.getCategoryBudget()<category.getCategoryBudget()){
                throw new ResponseStatusException(HttpStatus.NOT_EXTENDED);
            }
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    private void logExpenseUpdateToHistory(ExpenseCategory category,int balance,String name) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE_PATH, true))) {
            String logEntry = String.format("[%s] Category: %s, Amount Spent: %d,Amount Left: %d\n",
                    LocalDate.now(), name, category.getCategoryBudget(),balance);
            writer.write(logEntry);
        }
    }
    @Override
    public int amountRequired() {
        ArrayList<ExpenseCategory> a=new ArrayList<>(expenseCategoryJPARepository.findAll());
        int sum=0;
        for(ExpenseCategory i:a){
            sum+=i.getCategoryBudget();
        }
        return sum;
    }


}
