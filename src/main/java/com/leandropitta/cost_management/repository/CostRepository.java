package com.leandropitta.cost_management.repository;

import com.leandropitta.cost_management.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository  extends JpaRepository<Cost, Integer> {
}
