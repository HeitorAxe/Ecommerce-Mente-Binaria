package com.compassuol.sp.challenge.ecommerce.product.controller;

import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
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
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product newProduct){
        Product product = productService.createProduct(newProduct);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProductId(@PathVariable ("id") Long id, @RequestBody Product product){
        Optional<Product> product1 = productService.buscarPorId(id);

        if(product1.isPresent()){
            Product existingPorduct = product1.get();
            existingPorduct.setName(product.getName());
            existingPorduct.setDescription(product.getDescription());
            existingPorduct.setPrice(product.getPrice());

            productService.salvarProduto(existingPorduct);

            return ResponseEntity.status(HttpStatus.OK).body(existingPorduct);
        }else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");

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

}
