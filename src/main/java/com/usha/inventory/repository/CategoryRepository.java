package com.usha.inventory.repository;

import com.usha.inventory.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    // find category by name
    Category findByName(String name);

    // check if category exists by name
    boolean existsByName(String name);
}
