package com.murilonerdx.springgrpclearning.service;

import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;
import com.murilonerdx.springgrpclearning.exception.ProductAlreadyExistsException;
import com.murilonerdx.springgrpclearning.exception.ProductNotFoundException;
import com.murilonerdx.springgrpclearning.repository.ProductRepository;
import com.murilonerdx.springgrpclearning.util.ProductConverterUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService{

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductOutputDTO create(ProductInputDTO inputDTO) {
        checkDuplicity(inputDTO.getName());
        var product = ProductConverterUtil.productInputDtoToProductTo(inputDTO);
        var productCreate = this.repository.save(product);

        return ProductConverterUtil.productToProductOutputDto(productCreate);
    }

    @Override
    public ProductOutputDTO findById(Long id) {
        var product = this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return ProductConverterUtil.productToProductOutputDto(product);
    }

    @Override
    public void delete(Long id) {
        var product = this.repository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        this.repository.delete(product);
    }

    @Override
    public List<ProductOutputDTO> findAll() {
        var products = this.repository.findAll();
        return products.stream()
                .map(ProductConverterUtil::productToProductOutputDto)
                .collect(Collectors.toList());
    }

    private void checkDuplicity(String name){
        this.repository.findByNameIgnoreCase(name)
                .ifPresent(e->{
                    throw new ProductAlreadyExistsException(name);
                });
    }
}
