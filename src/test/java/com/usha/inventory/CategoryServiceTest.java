package com.usha.inventory;

import com.usha.inventory.exception.CategoryNotFoundException;
import com.usha.inventory.model.Category;
import com.usha.inventory.repository.CategoryRepository;
import com.usha.inventory.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void ShouldReturnCategoryWhenFound(){

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(1L);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound(){

        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CategoryNotFoundException.class, () ->{
            categoryService.findById(99L);
        });
    }

    @Test
    void shouldSaveCategorySuccessfully(){

        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronics items");

        when(categoryRepository.save(category)).thenReturn(category);

        Category saved = categoryService.save(category);

        assertNotNull(saved);
        assertEquals("Electronics", saved.getName());
        verify(categoryRepository, times(1)).save(category);

    }

    @Test
    void shouldReturnAllCategories(){

        Category cat1 = new Category();
        cat1.setName("Electronics");

        Category cat2 = new Category();
        cat2.setName("Clothing");

        List<Category> categories = List.of(cat1, cat2);

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(2, result.size());
        verify(categoryRepository, times(1)).findAll();

    }
    @Test
    void shouldDeleteCategoryById(){

       Category category = new Category();
       category.setId(1L);
       category.setName("Electronics");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.delete(1L);

        verify(categoryRepository, times(1)).findById(1L);
        verify(categoryRepository, times(1)).deleteById(1L);

    }

    @Test
    void shouldUpdateCategoryById(){

        Category existing = new Category();
        existing.setId(1L);
        existing.setName("Electronics");
        existing.setDescription("Electronics and gadgets");

        Category updated = new Category();
        updated.setName("Electronics & Gadgets");
        updated.setDescription("All electronic items");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(categoryRepository.save(existing)).thenReturn(existing);

        // Act
        Category result = categoryService.update(1L, updated);

        // Assert
        assertEquals("Electronics & Gadgets", result.getName());
        verify(categoryRepository, times(1)).save(existing);
    }


}


