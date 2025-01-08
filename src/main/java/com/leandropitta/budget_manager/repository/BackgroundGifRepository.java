package com.leandropitta.budget_manager.repository;

import com.leandropitta.budget_manager.entity.BackgroundColor;
import com.leandropitta.budget_manager.entity.BackgroundGif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BackgroundGifRepository extends JpaRepository<BackgroundGif, Long> {
}