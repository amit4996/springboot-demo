package com.example.demo.service;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    public List<Product> getProducts(){
        log.debug("Fetching all products");
        return productRepository.findAll();
    }

    public Product addProduct( Product product){
        Product savedProduct = productRepository.save(product);
        log.info("Successfully added product with ID: {}", savedProduct.getProductId());
        return savedProduct;
    }

    public Product updateProduct(Product product){
        Product updatedProduct = productRepository.save(product);
        log.info("Updating product ID: {}", updatedProduct.getProductId());
        return updatedProduct;
    }

    public void deleteProduct(Integer productId){
        if (!productRepository.existsById(productId)) {
            log.warn("Attempted to delete non-existent product ID: {}", productId);
            throw new ProductNotFoundException("Cannot delete. Product with ID " + productId + " does not exist.");
        }
        productRepository.deleteById(productId);
        log.info("Deleted product ID: {}", productId);
    }

    public Product getProductsById(Integer productId) {
        log.debug("Searching for product ID: {}", productId);
        return productRepository.findById(productId).orElseThrow(()-> {
            log.error("Product not found for ID: {}", productId);
            return new ProductNotFoundException("Product doesn't exist");
        });
    }
}
