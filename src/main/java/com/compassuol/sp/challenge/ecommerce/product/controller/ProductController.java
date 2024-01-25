package com.compassuol.sp.challenge.ecommerce.product.controller;

import com.compassuol.sp.challenge.ecommerce.exception.ErrorMessage;
import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.PageableMapper;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import com.compassuol.sp.challenge.ecommerce.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="Products", description = "Contains all operations to register, edit, delete, view a product.")
@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create a new product", description = "Feature to create a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "product already registered in the system",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductCreateDTO createDto){
        Product product = productService.createProduct(ProductMapper.toProduct(createDto));
        return  ResponseEntity.status(HttpStatus.CREATED).body(ProductMapper.toDTO(product));
    }

    @Operation(summary = "Get all products as pageable", description = "Retrieve products as pageable",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List of products retrieved successfully as pageable",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = PageableDTO.class)))
            })
    @GetMapping("/page")
    public ResponseEntity<PageableDTO> getAllAsPage(@PageableDefault(size = 5)Pageable pageable){
        Page<ProductProjection> projection = productService.getAllAsPage(pageable);
        PageableDTO dto = PageableMapper.toDTO(projection);
        return ResponseEntity.ok(dto);
    }

    @Operation(summary = "List all registered products", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "List all registered products",
                            content = @Content(mediaType = "application/json;charset=UTF-8",
                                    array = @ArraySchema(schema = @Schema(implementation = ProductResponseDTO.class))))
            })
    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(){
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(ProductMapper.toListDTO(products));
    }



    @Operation(summary = "Delete a product by ID", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id){
        productService.remove(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve a product by id", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        return ResponseEntity.ok(ProductMapper.toDTO(product));
    }

    @Operation(summary = "update product by id", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully updated",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ProductResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProductId(@PathVariable("id") Long id, @Valid @RequestBody ProductUpdateDTO productDto) {
        Product product = productService.getById(id);
        ProductMapper.updateByDto(productDto, product);
        productService.updateProduct(product);
        return ResponseEntity.ok(ProductMapper.toDTO(product));
    }

}
