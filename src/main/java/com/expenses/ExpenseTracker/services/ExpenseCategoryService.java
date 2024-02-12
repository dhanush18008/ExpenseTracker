//package com.expenses.ExpenseTracker.services;
//
//import com.expenses.ExpenseTracker.dao.ExpenseCategory;
//import com.expenses.ExpenseTracker.repo.ExpenseCategoryRepository;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//import java.util.*;
//@Service
//public class ExpenseCategoryService implements ExpenseCategoryRepository {
//    HashMap<Integer, ExpenseCategory> bucketList=new HashMap<>();
//    private int uniqueCategoryId=1;
//    @Override
//    public ArrayList<ExpenseCategory> getAllCategories() {
//        Collection<ExpenseCategory> allCategories=bucketList.values();
//        return new ArrayList<>(allCategories);
//    }
//    @Override
//    public ExpenseCategory getCategoryById(int categoryId) {
//        ExpenseCategory expensecategory=bucketList.get(categoryId);
//        if(expensecategory==null){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//        return expensecategory;
//    }
//    //
//
//
//
//    @Override
//    public ExpenseCategory createCategory(ExpenseCategory category) {
//        category.setCategoryId(uniqueCategoryId);
//        bucketList.put(uniqueCategoryId,category);
//        uniqueCategoryId+=1;
//        return category;
//    }
//    @Override
//    public ExpenseCategory updateCategory(int categoryId, ExpenseCategory category) {
//       ExpenseCategory existingCategory=bucketList.get(categoryId);
//       if(existingCategory==null){
//           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//       }
//       if(category.getCategoryName()!=null){
//           existingCategory.setCategoryName(category.getCategoryName());
//       }
//       if(category.getCategoryBudget()!=0){
//            existingCategory.setCategoryBudget(category.getCategoryBudget());
//       }
//       return existingCategory;
//    }
//    @Override
//    public void deleteCategory(int categoryId) {
//       ExpenseCategory expenseCategory=bucketList.get(categoryId);
//       if(expenseCategory==null){
//           throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//       }else{
//           bucketList.remove(categoryId);
//           throw new ResponseStatusException(HttpStatus.NO_CONTENT);
//       }
//    }
//
//    @Override
//    public ExpenseCategory spend(int categoryId, ExpenseCategory category) {
//        return null;
//    }
//
//    @Override
//    public int amountRequired() {
//        return 0;
//    }
//}
