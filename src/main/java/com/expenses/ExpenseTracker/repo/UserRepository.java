package com.expenses.ExpenseTracker.repo;


import com.expenses.ExpenseTracker.dao.User;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
@Repository
public interface UserRepository{
    ArrayList<User> getAllUsers();
    User getUserByName(String userName);
    User createUser(User user);
    User updateUser(String UserName, User user);
    void deleteUser(String userName);
}
