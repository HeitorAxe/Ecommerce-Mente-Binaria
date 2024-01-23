package com.compassuol.sp.challenge.ecommerce.product.service;

import com.compassuol.sp.challenge.ecommerce.product.entity.Product;
import com.compassuol.sp.challenge.ecommerce.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    @Transactional
    public Product createProduct(Product newProduct) {
        return productRepository.save(newProduct);
    }

    public void remove(Long id) {
        productRepository.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Product buscarPorId(Long id){
        return productRepository.findById(id).orElseThrow(
                ()->new RuntimeException("Produto não encontrado")
        );


    }
}
