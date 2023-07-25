package com.example.smallstore.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    private Long categoryNumber;

    @Column(nullable = false)
    private String categoryName;
}
