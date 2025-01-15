package com.leandropitta.budget_manager.repository;

import com.leandropitta.budget_manager.entity.FontFamily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FontFamilyRepository extends JpaRepository<FontFamily, Long> {
}