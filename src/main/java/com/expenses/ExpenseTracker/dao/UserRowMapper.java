package com.expenses.ExpenseTracker.dao;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
//        return new ExpenseCategory(rs.getInt("categoryId"),
//                rs.getString("categoryName"),
//                rs.getInt("categoryBudget"),
//                rs.getString("cDate"));
        return new User(rs.getInt("userId"),
                rs.getString("userName"),
                rs.getString("userDate")
                );
    }
}
//