package com.expenses.ExpenseTracker.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@Entity
@Table(name ="users")
public class User {
     @Column(name = "userid")
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int userId;
     @Id
     @Column(name = "username")
     private String userName;
     @Column(name = "userDate")
     private String userDate;
     public User(){

     }

     public User(int userId, String userName, String userDate) {
          this.userId = userId;
          this.userName = userName;
          this.userDate = userDate;
     }
}
//