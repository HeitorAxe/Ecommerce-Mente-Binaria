package com.compassuol.sp.challenge.ecommerce.product.controller;

import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ProductService productService;
    @MockBean
    ProductRepository productRepository;

    @Test
    void createProduct_WithValidData_ReturnsCreated() throws Exception {

        when(productService.createProduct(PRODUCT_CREATE_DTO)).thenReturn(PRODUCT_RESPONSE_DTO);

        mockMvc.perform(post("/products")
                        .content(objectMapper.writeValueAsString(PRODUCT_RESPONSE_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void removeProduct_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void updateProduct_WithValidaDatas_ReturnsNewProduct() throws Exception {
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(PRODUCT));

        mockMvc.perform(put("/products/{id}", 1L)
                        .content(objectMapper.writeValueAsString(PRODUCT_RESPONSE_DTO))
                        .contentType(
                                MediaType.APPLICATION_JSON
                        ))
                .andExpect(status().isOk());
    }

    @Test
    void getProduct_ByExistingId_ReturnsProduct() throws Exception {
        when(productService.getById(1L)).thenReturn(PRODUCT_RESPONSE_DTO);

        mockMvc.perform(
                        get("/products/1")
                )
                .andExpect(status().isOk());
    }
   @Test
   void getProduct_ByNonexistingId_ReturnsNotFound() throws Exception {
       when(productService.getById(anyLong())).thenThrow(ProductNameUniqueViolationException.class);
        mockMvc.perform(get("/products/0")).andExpect(status().isNotFound());
    }

    @Test
    void listProduct_ReturnsAllProducts() throws Exception {
        List<ProductResponseDTO> products = new ArrayList<>();
        products.add(PRODUCT_RESPONSE_DTO);
        when(productService.getAll()).thenReturn(products);

        mockMvc
                .perform(
                        get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

    }

}
