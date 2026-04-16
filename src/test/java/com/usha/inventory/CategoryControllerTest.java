package com.usha.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.usha.inventory.controller.CategoryController;
import com.usha.inventory.model.Category;
import com.usha.inventory.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldSaveCategoryAndReturn200() throws Exception{

        Category category = new Category();
        category.setName("Electronics");
        category.setDescription("Electronics Item");

        when(categoryService.save(any(Category.class))).thenReturn(category);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    void shouldReturnCategoryByID() throws Exception{

        Category category = new Category();
        category.setId(1L);
        category.setName("Electronics");

        when(categoryService.findById(1L)).thenReturn(category);

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Electronics"));
    }

    @Test
    void shouldReturnAllCategory() throws Exception{

        Category cat1 = new Category();
        cat1.setName("Electronics");

        Category cat2 = new Category();
        cat2.setName("Clothing");

        List<Category> categories = List.of(cat1,cat2);

        when(categoryService.findAll()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Electronics"))
                .andExpect(jsonPath("$[1].name").value("Clothing"));
    }
}
