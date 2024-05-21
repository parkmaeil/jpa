package com.example.jpa.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Book {   //  Book --ORM--> book(table)

    @Id // PK
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동증가컬럼
    private Long id;

    @Column(length = 50, nullable = false, unique = true)
    private String title;
    private int price;
    private String author;
    private int page;
}
