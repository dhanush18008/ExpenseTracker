package com.expenses.ExpenseTracker.dao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
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
    @Column(name = "categoryname")
    private String categoryName;
    @Column(name = "categorybudget")
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
