package com.example.smallstore.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "shop")
public class shop {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shop_num;

    @Column(nullable = false)
    private String shop_name;

    @Id
    @Column(nullable = false)
    private String shop_number;

    @Column(nullable = false)
    private String shop_owner;

    @Column(unique = true, nullable = false)
    private String shop_address;

    @Column()
    private String shop_phoneNumber;

    @Column()
    private String shop_logo;

    @Column()
    private String shop_picture;

    @Column()
    private String category_name;
}
