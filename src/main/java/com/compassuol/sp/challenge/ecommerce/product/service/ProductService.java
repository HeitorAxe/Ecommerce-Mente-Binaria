package com.compassuol.sp.challenge.ecommerce.product.service;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.exception.ProductNameUniqueViolationException;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product createProduct(Product newProduct) {
        try{
            return productRepository.save(newProduct);
        }catch (DataIntegrityViolationException ex){
            throw new ProductNameUniqueViolationException(String.format("Produto de nome %s já existe", newProduct.getName()));
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
                () -> new EntityNotFoundException(String.format("Produto de id %s não encontrado", id))
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
    public Product updateProduct(Long id, Product updatedProduct) {
        checkIfProductNameExists(updatedProduct.getName(), id);
        Product existingProduct = getById(id);
        existingProduct.setName(updatedProduct.getName());
        existingProduct.setPrice(updatedProduct.getPrice());
        existingProduct.setDescription(updatedProduct.getDescription());

        return productRepository.save(existingProduct);
    }
    private void checkIfProductNameExists(String newName, Long currentProductId) {
        Product existingProductWithSameName = productRepository.findByNameIgnoreCase(newName);

        if (existingProductWithSameName != null && !existingProductWithSameName.getId().equals(currentProductId)) {
            throw new ProductNameUniqueViolationException(String.format("Product with name %s already exists", newName));
        }
    }
}


