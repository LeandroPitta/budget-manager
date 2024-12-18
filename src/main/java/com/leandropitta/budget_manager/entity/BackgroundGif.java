package com.leandropitta.budget_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "background_gif")
@Data
public class BackgroundGif {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
}
