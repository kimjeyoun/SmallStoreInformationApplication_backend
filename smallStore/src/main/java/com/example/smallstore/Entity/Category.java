package com.example.smallstore.Entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @Column(unique = true, nullable = false)
    private Long num;

    @Column(nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<Shop> shop;
}
