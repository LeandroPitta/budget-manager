package com.leandropitta.budget_manager.repository;

import com.leandropitta.budget_manager.entity.BudgetGif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetGifRepository extends JpaRepository<BudgetGif, Long> {
}