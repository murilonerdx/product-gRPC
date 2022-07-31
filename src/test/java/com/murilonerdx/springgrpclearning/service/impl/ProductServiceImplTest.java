package com.murilonerdx.springgrpclearning.service.impl;

import com.murilonerdx.springgrpclearning.domain.Product;
import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;
import com.murilonerdx.springgrpclearning.repository.ProductRepository;
import com.murilonerdx.springgrpclearning.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("when create product service is call with valid data a product is returned")
    public void createProductSuccessTest(){
        Product product = new Product(1L,
                "product name",
                5.00,
                5);


        when(productRepository.save(any()))
                .thenReturn(product);

        ProductInputDTO productInputDto = new ProductInputDTO(
                "product name",
                5.00,
                5);

        ProductOutputDTO productOutputDTO = productService.create(productInputDto);

        assertThat(productOutputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }
}
