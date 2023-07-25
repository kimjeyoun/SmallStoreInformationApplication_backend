package com.example.smallstore.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shop")
public class Shop {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shopNum;

    @Column(nullable = false)
    private String shopName;

    @Id
    @Column(nullable = false)
    private String shopNumber;

    @Column(nullable = false)
    private String shopOwner;

    @Column(unique = true, nullable = false)
    private String shopAddress;

    @Column()
    private String shopPhoneNumber;

    @Column()
    private String shopLogo;

    @Column()
    private String shopPicture;

    @Column()
    private String categoryName;
}
