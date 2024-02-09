package com.expenses.ExpenseTracker.services;

import com.expenses.ExpenseTracker.dao.User;
import com.expenses.ExpenseTracker.dao.UserRowMapper;
import com.expenses.ExpenseTracker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
@Service
public class UserH2Service implements UserRepository {
    @Autowired
    private JdbcTemplate db;
    @Override
    public ArrayList<User> getAllUsers() {
         return new ArrayList<>(db.query("SELECT* FROM USERS",new UserRowMapper()));
    }

    @Override
    public User getUserByName(String userName) {
        try{
            User user=db.queryForObject("SELECT* FROM USERS WHERE USERNAME=?",new UserRowMapper(),userName);
            return user;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User createUser(User user) {
        db.update("INSERT INTO USERS(userName,userDate) VALUES(?,?)",user.getUserName(),user.getUserDate());
        User savedUser=db.queryForObject("SELECT* FROM USERS WHERE userName=? AND userDate=?",new UserRowMapper(),user.getUserName(),user.getUserDate());
        return savedUser;
    }

    @Override
    public User updateUser(String UserName, User user) {
        if(user.getUserDate()!=null){
            db.update("UPDATE users SET userDate=? WHERE userName=?",user.getUserDate(),UserName);
        }

        return getUserByName(UserName);
    }

    @Override
    public void deleteUser(String userName) {
        db.update("DELETE FROM USERS WHERE userName=?",userName);
    }
}
