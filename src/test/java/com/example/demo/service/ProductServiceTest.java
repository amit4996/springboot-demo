package com.example.demo.service;

import com.example.demo.exception.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductService productService;

    @Test
    void getProducts() {

    }

    @Test
    void addProduct() {
        //given
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("ProductA");
        product.setPrice(1000);

        Mockito.when(productRepository.save(product)).thenReturn(product);

        //when
        Product addedProduct =productService.addProduct(product);

        //then
        Assertions.assertEquals(addedProduct,product);
    }

    @Test
    void updateProduct() {
        //given
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("ProductA");
        product.setPrice(1000);

        Mockito.when(productRepository.save(product)).thenReturn(product);

        //when
        Product updatedProduct =productService.updateProduct(product);

        //then
        Assertions.assertEquals(updatedProduct,product);
    }

    @Test
    void deleteProduct_whenProductExists() {
        // given
        Integer productId = 1;
        Mockito.when(productRepository.existsById(productId)).thenReturn(true);

        // when
        productService.deleteProduct(productId);

        // then
        Mockito.verify(productRepository).existsById(productId);
        Mockito.verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_whenProductDoesNotExist_shouldThrowException() {
        // given
        Integer productId = 99;
        Mockito.when(productRepository.existsById(productId)).thenReturn(false);

        // when + then
        ProductNotFoundException exception =
                Assertions.assertThrows(ProductNotFoundException.class,
                        () -> productService.deleteProduct(productId));

        Assertions.assertEquals(
                "Cannot delete. Product with ID 99 does not exist.",
                exception.getMessage()
        );

        Mockito.verify(productRepository).existsById(productId);
        Mockito.verify(productRepository, Mockito.never()).deleteById(Mockito.any());
    }

    @Test
    void getProductsById_whenProductExists_shouldReturnProduct() {
        // given
        Integer productId=1;
        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Laptop");
        product.setPrice(50000);

        Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.of(product));

        // when
        Product result = productService.getProductsById(productId);

        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(productId, result.getProductId());
        Assertions.assertEquals("Laptop", result.getProductName());

        Mockito.verify(productRepository).findById(productId);
    }

    @Test
    void getProductsById_whenProductDoesNotExist_shouldThrowException() {
        // given
        Integer productId = 99;

        Mockito.when(productRepository.findById(productId))
                .thenReturn(Optional.empty());

        // when + then
        ProductNotFoundException exception =
                Assertions.assertThrows(ProductNotFoundException.class,
                        () -> productService.getProductsById(productId));

        Assertions.assertEquals("Product doesn't exist", exception.getMessage());

        Mockito.verify(productRepository).findById(productId);
    }
}