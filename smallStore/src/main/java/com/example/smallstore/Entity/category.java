package com.example.smallstore.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "category")
public class category {
    @Id
    private Long category_number;

    @Column(nullable = false)
    private String category_name;
}
