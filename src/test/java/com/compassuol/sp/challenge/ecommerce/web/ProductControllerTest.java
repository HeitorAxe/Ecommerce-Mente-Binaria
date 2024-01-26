package com.compassuol.sp.challenge.ecommerce.web;

import com.compassuol.sp.challenge.ecommerce.product.controller.ProductController;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.compassuol.sp.challenge.ecommerce.common.ProductConstants.PRODUCT;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    public void removeProduct_WithExistingId_ReturnsNoContent() throws Exception {
        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
        @Test
        public void updateProduct_WithValidaDatas_ReturnsNewProduct() throws Exception {
        when(productService.getById(anyLong())).thenReturn(PRODUCT);

        mockMvc.perform(put("/products/{id}", 1L).content(objectMapper.writeValueAsString(PRODUCT)).contentType(

                                MediaType.APPLICATION_JSON
                        )

                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(PRODUCT));
    }


}
