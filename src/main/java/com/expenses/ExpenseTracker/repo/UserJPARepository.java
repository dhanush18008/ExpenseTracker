package com.expenses.ExpenseTracker.repo;

import com.expenses.ExpenseTracker.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJPARepository extends JpaRepository<User,String> {}
