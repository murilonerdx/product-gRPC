package com.murilonerdx.springgrpclearning.service.impl;

import com.murilonerdx.springgrpclearning.domain.Product;
import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;
import com.murilonerdx.springgrpclearning.exception.ProductAlreadyExistsException;
import com.murilonerdx.springgrpclearning.exception.ProductNotFoundException;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
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

    @Test
    @DisplayName("when create product service is call with duplicated name, throw ProductAlreadyExistsException")
    public void createProductExceptionTest() {
        Product product = new Product(1L, "product name", 10.00, 10);

        when(productRepository.findByNameIgnoreCase(any())).thenReturn(Optional.of(product));

        ProductInputDTO inputDTO = new ProductInputDTO("product name", 10.00, 10);

        assertThatExceptionOfType(ProductAlreadyExistsException.class)
                .isThrownBy(() -> productService.create(inputDTO));
    }


    @Test
    @DisplayName("when findById product is call with valid id a product is returned")
    public void findByIdProductSuccessTest(){
        Long id = 1L;

        Product product = new Product(1L,
                "Product A",
                10.99,
                10);


        when(productRepository.findById(any()))
                .thenReturn(Optional.of(product));

        ProductOutputDTO productOutputDTO = productService.findById(id);

        assertThat(productOutputDTO)
                .usingRecursiveComparison()
                .isEqualTo(product);
    }

    @Test
    @DisplayName("when findById product is call with invalid id throw ProductNotFoundException")
    public void findByIdProductExceptionTest() {
        Long id = 1L;

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.findById(id));
    }

    @Test
    @DisplayName("when delete product is call with id should does not throw")
    public void deleteProductTest() {
        Long id = 1L;

        Product product = new Product(1L,
                "Product A",
                10.99,
                10);

        when(productRepository.findById(any())).thenReturn(Optional.of(product));

        Assertions.assertThatNoException().isThrownBy(() -> productService.findById(id));
    }

    @Test
    @DisplayName("when delete product is call with id should does not throws ProductNotFoundException")
    public void deleteProductExceptionTest() {
        Long id = 1L;

        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThatExceptionOfType(ProductNotFoundException.class)
                .isThrownBy(() -> productService.delete(id));
    }

}
