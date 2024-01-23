package com.compassuol.sp.challenge.ecommerce.product.service;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import com.compassuol.sp.challenge.ecommerce.product.repository.projection.ProductProjection;
import lombok.RequiredArgsConstructor;
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
        return productRepository.save(newProduct);
    }

    @Transactional
    public void remove(Long id) {
        productRepository.deleteById(id);
    }
<<<<<<< HEAD
    @Transactional(readOnly = true)
    public Product buscarPorId(Long id){
        return productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Produto não encontrado")
        );


=======

    @Transactional(readOnly = true)
    public Page<ProductProjection> getAllAsPage(Pageable pageable){
        return productRepository.findAllAsProjection(pageable);
    }

    @Transactional(readOnly = true)
    public List<Product> getAll(){
        return productRepository.findAll();
>>>>>>> 27d15489f418a4a3e4955b5ec84c7f68f984659c
    }
}


