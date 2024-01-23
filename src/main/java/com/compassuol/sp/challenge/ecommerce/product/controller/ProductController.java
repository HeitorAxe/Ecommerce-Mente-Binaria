package com.compassuol.sp.challenge.ecommerce.product.controller;

import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.PageableMapper;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO createDto){
        Product product = productService.createProduct(ProductMapper.toProduct(createDto));
        return  ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(product));
    }

    @GetMapping("/page")
    public ResponseEntity<PageableDTO> getAllAsPage(@PageableDefault(size = 5)Pageable pageable){
        Page<ProductProjection> projection = productService.getAllAsPage(pageable);
        PageableDTO dto = PageableMapper.toDTO(projection);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(ProductMapper.toListDTO(products));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(ProductMapper.toDTO(product));
    }

}
