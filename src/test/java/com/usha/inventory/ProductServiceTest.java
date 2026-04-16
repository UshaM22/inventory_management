package com.usha.inventory;

import com.usha.inventory.exception.ProductNotFoundException;
import com.usha.inventory.model.Product;
import com.usha.inventory.repository.ProductRepository;
import com.usha.inventory.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldReturnProductWhenFound(){

        Product product = new Product();
        product.setId(1L);
        product.setName("Laptop");

        when(productRepository.findById(1l)).thenReturn(Optional.of(product));

        Product result = productService.findById(1l);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
    }

    @Test
    void shouldThrowExceptionWhenProductNotFound(){

        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () ->
        { productService.findById(99L);
        });
    }

    @Test
    void shouldSaveProductSuccessfully(){

        Product product = new Product();
        product.setName("Laptop");
//        product.setQuantity(2);
//        product.setPrice(999);
//        product.setDescription("Gaming Laptop");

        when(productRepository.save(product)).thenReturn(product);

        Product saved = productService.save(product);

        assertNotNull(saved);
        assertEquals("Laptop", saved.getName());
        verify(productRepository, times(1)).save(product);

    }

}
