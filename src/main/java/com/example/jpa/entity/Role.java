package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 3 4 5

    @Column(unique = true)
    private String name; // USER, MANAGE, ADMIN, ........
}
