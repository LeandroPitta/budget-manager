package com.leandropitta.budget_manager.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "background_color", nullable = false)
    private BackgroundColor backgroundColor;

    @ManyToOne
    @JoinColumn(name = "title_color", nullable = false)
    private TitleColor titleColor;

    @ManyToOne
    @JoinColumn(name = "font_family", nullable = false)
    private FontFamily fontFamily;

    @Column(name = "budget_gif", nullable = false)
    private String budgetGif;

    @ManyToOne
    @JoinColumn(name = "background_gif", nullable = false)
    private BackgroundGif backgroundGif;
}
