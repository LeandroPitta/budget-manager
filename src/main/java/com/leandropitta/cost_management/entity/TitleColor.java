package com.leandropitta.cost_management.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "title_color")
@Data
public class TitleColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;
}
