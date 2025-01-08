package com.leandropitta.budget_manager.repository;

import com.leandropitta.budget_manager.entity.BackgroundColor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundColorRepository extends JpaRepository<BackgroundColor, Long> {
}