package com.expenses.ExpenseTracker.dao;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


@Setter
@Getter
@Component
public class ExpenseCategory {
    private int categoryId;
    private String categoryName;
    private int categoryBudget;
    private String date;

    public ExpenseCategory(){}

    public ExpenseCategory(int categoryId, String categoryName, int categoryBudget, String date) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryBudget = categoryBudget;
        this.date = date;
    }

}
