package com.leandropitta.budget_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "font_family")
@Data
public class FontFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
}
