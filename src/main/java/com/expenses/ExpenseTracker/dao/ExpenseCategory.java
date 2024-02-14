package com.expenses.ExpenseTracker.dao;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@Entity
@Valid
@Table(name = "categories")
public class ExpenseCategory {
    @Id
    @Column(name = "categoryid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Column(name = "categoryname")
    @NotNull(message = "Name may not be null")
    @Size(min = 4, max = 30,message = "category name should be greater than 4 characters and less than 30 characters")
    private String categoryName;
    @Max(value = 100000, message = "Budget should not be greater than 100000")
    @Column(name = "categorybudget")
    @Min(value = 0, message = "You Reached Your Limit")
    private int categoryBudget;
    @Column(name = "dateofcreation")
    private String dateOfCreation;
    @Column(name = "lastupdateddate")
    private String lastUpdatedDate;
    public ExpenseCategory(){}

    public ExpenseCategory(int categoryId, String categoryName, int categoryBudget, String dateOfCreation, String lastUpdatedDate) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryBudget = categoryBudget;
        this.dateOfCreation = dateOfCreation;
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
