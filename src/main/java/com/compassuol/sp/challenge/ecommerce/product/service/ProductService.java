package com.compassuol.sp.challenge.ecommerce.product.service;

import com.compassuol.sp.challenge.ecommerce.product.dto.PageableDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductCreateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductResponseDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.ProductUpdateDTO;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.PageableMapper;
import com.compassuol.sp.challenge.ecommerce.product.dto.mapper.ProductMapper;
import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDTO createProduct(ProductCreateDTO newProduct) {
        try {
            return ProductMapper.toDTO(productRepository.save(ProductMapper.toProduct(newProduct)));
        } catch (DataIntegrityViolationException ex) {
            throw new ProductNameUniqueViolationException(String.format("Product with name %s already exists", newProduct.getName()));
        }
    }

    @Transactional
    public void remove(Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product id %d not found", id))
        );
        productRepository.deleteById(product.getId());
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO getById(Long id) {
        return ProductMapper.toDTO(productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product id %d not found", id))
        ));
    }

    @Transactional(readOnly = true)
    public PageableDTO getAllAsPage(Pageable pageable) {
        return PageableMapper.toDTO(productRepository.findAllAsProjection(pageable));
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDTO> getAll() {
        return ProductMapper.toListDTO(productRepository.findAll());
    }

    @Transactional
    public ProductResponseDTO updateProduct(ProductUpdateDTO productUpdate, Long id) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product id %d not found", id))
        );
        Product existing = productRepository.findByName(productUpdate.getName());
        if (existing != null && !existing.getId().equals(product.getId()))
            throw new ProductNameUniqueViolationException(
                    String.format("Product with name %s already exists", productUpdate.getName())
            );
        ProductMapper.updateByDto(productUpdate, product);
        productRepository.save(product);
        return ProductMapper.toDTO(product);
    }
}


