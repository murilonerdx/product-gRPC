package com.murilonerdx.springgrpclearning.util;

import com.murilonerdx.springgrpclearning.domain.Product;
import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ProductConverterUtilTest {

    @Test
    public void productToProductOutputDtoTest(){
        var product = new Product(1L, "product name", 10.00, 10);

        var productOutputDto = ProductConverterUtil.
                productToProductOutputDto(product);

        Assertions.assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(productOutputDto);

    }

    @Test
    public void productToProductInputDtoTest(){
        var productInput = new ProductInputDTO("product name", 10.00, 10);

        var product = ProductConverterUtil.
                productInputDtoToProductTo(productInput);

        Assertions.assertThat(productInput)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }
}
