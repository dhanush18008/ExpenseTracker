package com.expenses.ExpenseTracker.services;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryJPARepository;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ExpenseCategoryJPAService implements ExpenseCategoryRepository {
    @Autowired
    private ExpenseCategoryJPARepository expenseCategoryJPARepository;
    public static final String HISTORY_FILE_PATH = "src/main/resources/expense_history.txt";

    @Override
    public ArrayList<ExpenseCategory> getAllCategories() {
        return new ArrayList<>(expenseCategoryJPARepository.findAll());
    }

    @Override
    public ExpenseCategory getCategoryById(int categoryId) {
        //try {
        ExpenseCategory expenseCategory = expenseCategoryJPARepository.findById(categoryId).get();
        return expenseCategory;
        //}catch(Exception e) {
        //throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        //}
    }


    @Override
    public ExpenseCategory createCategory(ExpenseCategory category) {
        expenseCategoryJPARepository.save(category);
        return category;
    }

    @Override
    public ExpenseCategory updateCategory(int categoryId, ExpenseCategory category) {
        ExpenseCategory newExpenseCategory = expenseCategoryJPARepository.findById(categoryId).get();
        if (category.getCategoryName() != null) {
            newExpenseCategory.setCategoryName(category.getCategoryName());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if (category.getCategoryBudget() != 0) {
            newExpenseCategory.setCategoryBudget(category.getCategoryBudget());
        }
        category.setLastUpdatedDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString()); // Format date
        expenseCategoryJPARepository.save(newExpenseCategory);
        return newExpenseCategory;
    }

    @Override
    public void deleteCategory(int categoryId) {
        try {
            expenseCategoryJPARepository.deleteById(categoryId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


    @Override
    public ExpenseCategory spend(ExpenseCategory category) {

        int categoryId = category.getCategoryId();
        @Valid ExpenseCategory expenseCategory = getCategoryById(categoryId);

        expenseCategory.setLastUpdatedDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString());

        expenseCategory.setCategoryBudget(expenseCategory.getCategoryBudget() - category.getCategoryBudget());
        try {
            logExpenseUpdateToHistory(category, expenseCategory.getCategoryBudget(), expenseCategory.getCategoryName());
        }catch(Exception e) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
            expenseCategoryJPARepository.save(expenseCategory);
            return expenseCategory;
}

    private void logExpenseUpdateToHistory(ExpenseCategory category,int balance,String name) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(HISTORY_FILE_PATH, true))) {
            String logEntry = String.format("[%s] Category: %s, Amount Spent: %d,Amount Left: %d\n",
                    LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")).toString(), name, category.getCategoryBudget(),balance);
            writer.write(logEntry);
        }
    }
    @Override
    public Map<String,Integer> amountRequired() {
        List<ExpenseCategory> expenseCategories = getAllCategories();
        Map<String, Integer> categoryBudgets = new HashMap<>();
        int sum=0;

        for (ExpenseCategory category : expenseCategories) {
            categoryBudgets.put(category.getCategoryName(), category.getCategoryBudget());
            sum+=category.getCategoryBudget();
        }
        categoryBudgets.put("Total : ",sum);
        return categoryBudgets;
    }


}
