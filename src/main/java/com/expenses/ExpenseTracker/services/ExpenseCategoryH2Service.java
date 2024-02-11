package com.expenses.ExpenseTracker.services;
import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import com.expenses.ExpenseTracker.dao.ExpenseCategoryRowMapper;
import com.expenses.ExpenseTracker.repo.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
@Service
public class ExpenseCategoryH2Service implements ExpenseCategoryRepository {
    @Autowired
    private JdbcTemplate db;
    @Override
    public ArrayList<ExpenseCategory> getAllCategories() {
        return  new ArrayList<>(db.query("SELECT* FROM categories",new ExpenseCategoryRowMapper()));
    }
//
    @Override
    public ExpenseCategory getCategoryById(int categoryId) {
        try{
            ExpenseCategory expenseCategory=db.queryForObject("SELECT* FROM CATEGORIES WHERE CATEGORYID=?",new ExpenseCategoryRowMapper(),categoryId);
            return expenseCategory;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }


    @Override
    public ExpenseCategory createCategory(ExpenseCategory category) {
        db.update("INSERT INTO CATEGORIES(categoryName,categoryBudget,cDate) VALUES(?,?,?)",category.getCategoryName(),category.getCategoryBudget(),category.getDate());
        ExpenseCategory savedCategory=db.queryForObject("SELECT* FROM CATEGORIES WHERE categoryName=? AND categoryBudget=?",new ExpenseCategoryRowMapper(),category.getCategoryName(),category.getCategoryBudget());
        return savedCategory;
    }

    @Override
    public ExpenseCategory updateCategory(int categoryId, ExpenseCategory category) {
        if(category.getCategoryName()!=null){
            db.update("UPDATE CATEGORIES SET categoryName=? WHERE categoryId=?",category.getCategoryName(),categoryId);
        }
        if(category.getCategoryBudget()!=0){
            db.update("UPDATE CATEGORIES SET categoryBudget=? WHERE categoryId=?",category.getCategoryBudget(),categoryId);
        }
        if(category.getDate()!=null){
            db.update("UPDATE CATEGORIES SET cDate =? WHERE categoryId=?",category.getDate(),categoryId);
        }
        return getCategoryById(categoryId);

    }

    @Override
    public void deleteCategory(int categoryId) {
//
        db.update("DELETE FROM CATEGORIES WHERE categoryId=?",categoryId);
    }

    @Override
    public ExpenseCategory spend(int categoryId, ExpenseCategory category) {
        return null;
    }
}
