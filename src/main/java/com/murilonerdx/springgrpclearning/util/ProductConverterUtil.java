package com.murilonerdx.springgrpclearning.util;

import com.murilonerdx.springgrpclearning.domain.Product;
import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;

public class ProductConverterUtil {

    public static ProductOutputDTO productToProductOutputDto(Product product){
        return new ProductOutputDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }

    public static Product productInputDtoToProductTo(ProductInputDTO product){
        return new Product(
                null,
                product.getName(),
                product.getPrice(),
                product.getQuantityInStock()
        );
    }


}
