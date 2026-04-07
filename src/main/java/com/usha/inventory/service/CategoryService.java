package com.usha.inventory.service;

import com.usha.inventory.exception.CategoryNotFoundException;
import com.usha.inventory.model.Category;
import com.usha.inventory.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category save(Category category){
        return categoryRepository.save(category);

    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new CategoryNotFoundException("Category not found with id:" +id));
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category update(Long id, Category category){
        Category existing = findById(id);
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());
        return categoryRepository.save(existing);
    }

    public void delete(Long id){
        categoryRepository.deleteById(id);
    }
}
