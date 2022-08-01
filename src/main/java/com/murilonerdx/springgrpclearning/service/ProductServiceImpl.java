package com.murilonerdx.springgrpclearning.service;

import com.murilonerdx.springgrpclearning.domain.Product;
import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;
import com.murilonerdx.springgrpclearning.exception.ProductAlreadyExistsException;
import com.murilonerdx.springgrpclearning.repository.ProductRepository;
import com.murilonerdx.springgrpclearning.util.ProductConverterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<ProductOutputDTO> findAll() {
        return null;
    }

    private void checkDuplicity(String name){
        this.repository.findByNameIgnoreCase(name)
                .ifPresent(e->{
                    throw new ProductAlreadyExistsException(name);
                });
    }
}
