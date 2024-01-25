package com.compassuol.sp.challenge.ecommerce.product.service;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product newProduct) {
        try{
            return productRepository.save(newProduct);
        }catch (DataIntegrityViolationException ex){
            throw new ProductNameUniqueViolationException(String.format("Product with name %s already exists", newProduct.getName()));
        }
    }

    @Transactional
    public void remove(Long id) {
        Product product = getById(id);
        productRepository.deleteById(product.getId());
    }

    @Transactional(readOnly = true)
    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("Product id %d not found", id))
        );
    }

    @Transactional(readOnly = true)
    public Page<ProductProjection> getAllAsPage(Pageable pageable){
        return productRepository.findAllAsProjection(pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll(){
        return productRepository.findAll();
    }

    @Transactional
    public void updateProduct(Product product){
        try {
            Product existingProduct = productRepository.findByName(product.getName());
            productRepository.save(product);
        } catch (DataIntegrityViolationException ex) {
            throw new ProductNameUniqueViolationException(
                    String.format("Product with name %s already exists", product.getName())
            );
        }
    }
}


