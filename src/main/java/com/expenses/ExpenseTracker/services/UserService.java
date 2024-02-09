package com.expenses.ExpenseTracker.services;


import com.expenses.ExpenseTracker.dao.User;
import com.expenses.ExpenseTracker.repo.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
@Service
@Component
public class UserService implements UserRepository{
 private HashMap<String,User> users=new HashMap<>();
private int uniqueId=1;

    @Override
    public ArrayList<User> getAllUsers(){
        //addData();
        Collection<User> allUser=users.values();
        return new ArrayList<User>(allUser);
    }
    //
    @Override
    public User getUserByName(String userName){
        //addData();
        User user=users.get(userName);
        if(user==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return user;
    }
    @Override
    public User createUser(User user){
        user.setUserId(uniqueId);

        users.put(user.getUserName(),user);

        uniqueId+=1;

        return user;
    }
    @Override
    public User updateUser(String UserName, User user){
        //addData();
        User existingUser=users.get(UserName);
        if(existingUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        if(user.getUserName()!=null){
            existingUser.setUserName(user.getUserName());
        }
        if(user.getUserId()!=0){
            existingUser.setUserId(user.getUserId());
        }

        //updateUserByName(UserName,existingUser);
        return existingUser;
    }
    @Override
    public void deleteUser(String userName){
        //addData();
        User existingUser=users.get(userName);
        if(existingUser==null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }else{
            users.remove(userName);
            //deleteUserByName(userName);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
    }

}
