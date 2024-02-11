package com.expenses.ExpenseTracker.dao;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.print.attribute.standard.MediaSize;


@Setter
@Getter
@Component
@Entity
@Table(name = "categories")
public class ExpenseCategory {
    @Id
    @Column(name = "categoryid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;
    @Id
    @Column(name = "category")
    private String categoryName;
    @Column(name = "categoryBudget")
    private int categoryBudget;
    @Column(name = "date")
    private String date;
//
    public ExpenseCategory(){}

    public ExpenseCategory(int categoryId, String categoryName, int categoryBudget, String date) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryBudget = categoryBudget;
        this.date = date;
    }

}
