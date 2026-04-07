package com.usha.inventory.repository;

import com.usha.inventory.model.Category;
import com.usha.inventory.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    // find products by Category
    List<Product> findByCategory(Category category );

    // find products by name containing keyword(for search)
    List<Product> findByNameContaining(String keyword);

}
