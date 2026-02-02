package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }

    @GetMapping("/product")
    public ResponseEntity<List<Product>> getProducts(){
        List<Product> products = productService.getProducts();
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Product> getProductsById(@PathVariable Integer productId){
        Product product = productService.getProductsById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @PostMapping("/product")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product){
         Product createdProduct = productService.addProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

   @PatchMapping("/product")
   public ResponseEntity<Product> updateProduct(@Valid @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(product);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
   }

   @DeleteMapping("/product/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int productId){
        productService.deleteProduct(productId);



        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
   }

}
