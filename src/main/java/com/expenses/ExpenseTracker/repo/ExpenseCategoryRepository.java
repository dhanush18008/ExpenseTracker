package com.expenses.ExpenseTracker.repo;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Map;

@Repository
public interface ExpenseCategoryRepository {
    public ArrayList<ExpenseCategory> getAllCategories();
    public ExpenseCategory getCategoryById(int categoryId);
    public ExpenseCategory createCategory(ExpenseCategory category);
    public ExpenseCategory updateCategory(int categoryId, ExpenseCategory category);
    public void deleteCategory(int categoryId);

    ExpenseCategory spend(int categoryId, ExpenseCategory category);

    Map<String,Integer> amountRequired();
}
//