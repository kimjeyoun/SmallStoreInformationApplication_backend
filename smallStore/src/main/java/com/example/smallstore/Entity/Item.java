package com.example.smallstore.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long num;

    @Column(nullable = false)
    private String itemName;

    @Column(nullable = false)
    private String itemPrice;

    @Column(nullable = false)
    private String itemInfo;

    @Column(nullable = false)
    private String itemImg;
}
