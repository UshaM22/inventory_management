package com.usha.inventory.repository;

import com.usha.inventory.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    //find orders by customer name
    List<Order> findByCustomerName(String customerName);

    // find orders by status
    List<Order> findByStatus(String status);

}

