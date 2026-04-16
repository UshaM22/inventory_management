package com.usha.inventory.controller;

import com.usha.inventory.model.Order;
import com.usha.inventory.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> save(@RequestBody Order order){
        return ResponseEntity.ok(orderService.placeOrder(order));
    }

    @GetMapping
    public ResponseEntity<List<Order>> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> findById(@PathVariable Long id){
        return ResponseEntity.ok(orderService.findById(id));
    }

    @GetMapping("/customer/{customerName}")
    public ResponseEntity<List<Order>> findByCustomerName(@PathVariable String customerName){
        return ResponseEntity.ok(orderService.findByCustomerName(customerName));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> findByStatus(String status){
        return ResponseEntity.ok(orderService.findByStatus(status));
    }

    @PutMapping
    public ResponseEntity<Order> updateStatus(@PathVariable Long id, @RequestBody String status){
        return ResponseEntity.ok(orderService.updateStatus(id, status));

    }

}
