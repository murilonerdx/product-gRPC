package com.murilonerdx.springgrpclearning.service;

import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {
    ProductOutputDTO create(ProductInputDTO inputDTO);
    ProductOutputDTO findById(Long id);
    void delete(Long id);
    List<ProductOutputDTO> findAll();
}
