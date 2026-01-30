package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table (name="Product")
public class Product {
    @Id
    private Integer productId;

    private String productName;

    private int price;
}
