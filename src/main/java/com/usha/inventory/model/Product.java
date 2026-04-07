package com.usha.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;


}
