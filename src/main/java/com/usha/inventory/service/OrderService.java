package com.usha.inventory.service;
import com.usha.inventory.exception.InsufficientStockException;
import com.usha.inventory.exception.OrderNotFoundException;
import com.usha.inventory.exception.ProductNotFoundException;
import com.usha.inventory.model.Order;
import com.usha.inventory.model.OrderItem;
import com.usha.inventory.model.Product;
import com.usha.inventory.repository.OrderRepository;
import com.usha.inventory.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Order findById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));
    }

    public List<Order> findByCustomerName(String customerName) {
        return orderRepository.findByCustomerName(customerName);
    }

    public List<Order> findByStatus(String status) {
        return orderRepository.findByStatus(status);
    }

    public Order updateStatus(Long id, String status) {
        Order existing = findById(id);
        existing.setStatus(status);
        return orderRepository.save(existing);
    }

    @Transactional
    public Order placeOrder(Order order) {

        // Step 1 — set order date and status
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        // Step 2 — process each order item
        double totalAmount = 0;

        for (OrderItem item : order.getOrderItems()) {

            // find product
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException("Product not found"));

            // check stock
            if (product.getQuantity() < item.getQuantity()) {
                throw new InsufficientStockException("Insufficient stock for: " + product.getName());
            }

            // reduce stock
            product.setQuantity(product.getQuantity() - item.getQuantity());
            productRepository.save(product);

            // set price from product
            item.setPrice(product.getPrice());

            // set order reference
            item.setOrder(order);

            // add to total
            totalAmount += item.getPrice() * item.getQuantity();
        }

        // Step 3 — set total and save
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

}
