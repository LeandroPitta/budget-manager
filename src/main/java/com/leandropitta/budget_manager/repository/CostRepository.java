package com.leandropitta.budget_manager.repository;

import com.leandropitta.budget_manager.entity.Cost;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CostRepository  extends JpaRepository<Cost, Long> {
    List<Cost> findByUserId(Long userId, Sort sort);
}
