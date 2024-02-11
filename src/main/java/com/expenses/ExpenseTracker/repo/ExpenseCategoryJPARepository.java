package com.expenses.ExpenseTracker.repo;

import com.expenses.ExpenseTracker.dao.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseCategoryJPARepository extends JpaRepository<ExpenseCategory,Integer> {}
