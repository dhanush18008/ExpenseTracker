package com.expenses.ExpenseTracker.services;

import com.expenses.ExpenseTracker.dao.User;
import com.expenses.ExpenseTracker.repo.UserJPARepository;
import com.expenses.ExpenseTracker.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;

@Service
public class UserJPAService implements UserRepository {
    @Autowired
    UserJPARepository userJPARepository;

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(userJPARepository.findAll());
    }
    @Override
    public User getUserByName(String userName) {
        try{
            User user=userJPARepository.findById(userName).get();
            return user;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public User createUser(User user) {
        userJPARepository.save(user);
        return user;
    }

    @Override
    public User updateUser(String UserName, User user) {
//        try{
//            Book newBook=bookJpaRepository.findById(bookId).get();
//            if (book.getName()!=null){
//                newBook.setName(book.getName());
//            }
//            if(book.getImageUrl()!=null){
//                newBook.setImageUrl(book.getImageUrl());
//            }
//            bookJpaRepository.save(newBook);
//            return newBook;
//        }catch (Exception e){
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }

        try {
            User newUser=userJPARepository.findById(UserName).get();
            if(user.getUserName()!=null){
                newUser.setUserName(user.getUserName());
            }
            if(user.getUserId()!=0){
                newUser.setUserId(user.getUserId());
            }
            if(user.getUserDate()!=null){
                newUser.setUserDate(user.getUserDate());
            }
            userJPARepository.save(newUser);
            return newUser;
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteUser(String userName) {
        try {
            userJPARepository.deleteById(userName);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}
