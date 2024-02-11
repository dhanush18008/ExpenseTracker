package com.expenses.ExpenseTracker.controller;

import com.expenses.ExpenseTracker.dao.User;
import com.expenses.ExpenseTracker.services.UserH2Service;
import com.expenses.ExpenseTracker.services.UserJPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

//
@RestController
public class UserController {
    @Autowired
    UserJPAService userservice;
    @GetMapping("/users")
    public ArrayList<User> getAllUsers() {
        return userservice.getAllUsers();
    }
    @GetMapping("/users/{userName}")
    public User getUserByName(@PathVariable String userName){
        return userservice.getUserByName(userName);
    }
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return userservice.createUser(user);
    }
    @PutMapping("/users/{userName}")
    public User updateUser(@PathVariable String userName,@RequestBody User user) {
        return userservice.updateUser(userName,user);
    }
    @DeleteMapping("/users/{userName}")
    public void deleteUser(@PathVariable String userName) {
        userservice.deleteUser(userName);
    }


}
