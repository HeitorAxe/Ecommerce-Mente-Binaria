package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.product.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)

public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    public void  createPlanet_WithValidData_ReturnsCreated() throws Exception{
        Product PRODUCT = new Product("Produto", "O melhor produto", 500.00);

        when(productService.createProduct(PRODUCT)).thenReturn(PRODUCT);

        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(PRODUCT))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").value(PRODUCT));
    }

    @Test
    public void removeProduct_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getPlanet_ByExistingId_ReturnsPlanet() throws Exception{
        when(productService.getById(1L)).thenReturn(PRODUCT);

        mockMvc.perform(
                        get("/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PRODUCT));
    }

    @Test
    public void getProduct_ByNonexistingId_ReturnsNotFound() throws Exception{
        when(productService.getById(anyLong())).thenThrow(ProductNameUniqueViolationException.class);
        mockMvc.perform(get("/products/0")).andExpect(status().isNotFound());
    }

    @Test
    void listProduct_ReturnsAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(PRODUCT);
       when(productService.getAll()).thenReturn(products);
        }

        mockMvc
                .perform(
                        get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }



}
