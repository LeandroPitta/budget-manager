package com.leandropitta.budget_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "budget_gif")
@Data
public class BudgetGif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
}
