package com.murilonerdx.springgrpclearning.dto;

import com.murilonerdx.springgrpclearning.domain.Product;

public class ProductOutputDTO {
    private final Long id;
    private final String name;
    private final Double price;
    private final Integer quantityInStock;

    public ProductOutputDTO(Long id, String name, Double price, Integer quantityInStock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantityInStock = quantityInStock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantityInStock() {
        return quantityInStock;
    }

    Product toProduct(){
        return new Product(this.id, this.name, this.price, this.quantityInStock);
    }
}
