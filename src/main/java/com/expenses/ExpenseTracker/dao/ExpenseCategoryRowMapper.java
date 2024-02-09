package com.expenses.ExpenseTracker.dao;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExpenseCategoryRowMapper implements RowMapper<ExpenseCategory> {


    @Override
    public ExpenseCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ExpenseCategory(rs.getInt("categoryId"),
                rs.getString("categoryName"),
                rs.getInt("categoryBudget"),
                rs.getString("cDate"));
    }
}




