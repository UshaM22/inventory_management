package com.usha.inventory.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name ="orderItems")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name ="product_id", nullable = false)
    private Product product;

    @Column
    private int quantity;

    @Column
    private double price;
}
