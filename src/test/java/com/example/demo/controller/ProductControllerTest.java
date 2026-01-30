package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@WebMvcTest
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    ProductService productService;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getProducts_shouldReturnListOfProducts() throws Exception {
        Product productA = new Product();
        productA.setProductId(1);
        productA.setProductName("Laptop");
        productA.setPrice(50000);

        Product productB = new Product();
        productB.setProductId(2);
        productB.setProductName("Phone");
        productB.setPrice(30000);

        List<Product> products = List.of(productA,productB);

        Mockito.when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].productName").value("Laptop"));
    }

    @Test
    void getProductsById_shouldReturnProduct() throws Exception {
        Product product = new Product();
        product.setProductId(1);
        product.setProductName("Laptop");
        product.setPrice(50000);

        Mockito.when(productService.getProductsById(1)).thenReturn(product);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/product/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Laptop"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(50000));
    }

    @Test
    void addProduct_shouldReturnCreatedProduct() throws Exception {
        Product input = new Product();
        input.setProductId(null);
        input.setProductName("Laptop");
        input.setPrice(50000);

        Product saved = new Product();
        saved.setProductId(1);
        saved.setProductName("Laptop");
        saved.setPrice(50000);

        Mockito.when(productService.addProduct(input)).thenReturn(saved);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Laptop"));
    }

    @Test
    void updateProduct_shouldReturnUpdatedProduct() throws Exception {
        Product updated = new Product();
        updated.setProductId(1);
        updated.setProductName("Laptop Pro");
        updated.setPrice(70000);

        Mockito.when(productService.updateProduct(updated)).thenReturn(updated);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("Laptop Pro"));
    }

    @Test
    void deleteProduct_shouldReturnNoContent() throws Exception {
        Mockito.doNothing().when(productService).deleteProduct(1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/product/1"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}