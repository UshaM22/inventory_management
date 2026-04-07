package com.usha.inventory.service;

import com.usha.inventory.exception.ProductNotFoundException;
import com.usha.inventory.model.Category;
import com.usha.inventory.model.Product;
import com.usha.inventory.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product save(Product product){
        return productRepository.save(product);
    }

    public Product findById(Long id){
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Product not found with: " +id));
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public List<Product> findByCategory(Category category){
        return productRepository.findByCategory(category);
    }

    public List<Product> search(String keyword){
        return productRepository.findByNameContaining(keyword);
    }

    public Product update(Long id, Product product){
        Product existing = findById(id);
        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setPrice(product.getPrice());
        existing.setQuantity(product.getQuantity());
        existing.setCategory(product.getCategory());
        return productRepository.save(existing);
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }
}
