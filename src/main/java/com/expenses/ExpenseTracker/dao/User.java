package com.expenses.ExpenseTracker.dao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;


import java.util.HashMap;

@Component
@Setter
@Getter
public class User {
     private int userId;
     private String userName;
     private String userDate;
     public User(){

     }

     public User(int userId, String userName, String userDate) {
          this.userId = userId;
          this.userName = userName;
          this.userDate = userDate;
     }
}
