package com.murilonerdx.springgrpclearning.resources;


import com.murilonerdx.springgrpclearning.*;
import com.murilonerdx.springgrpclearning.dto.ProductInputDTO;
import com.murilonerdx.springgrpclearning.dto.ProductOutputDTO;
import com.murilonerdx.springgrpclearning.exception.ProductAlreadyExistsException;
import com.murilonerdx.springgrpclearning.repository.ProductRepository;
import com.murilonerdx.springgrpclearning.service.IProductService;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class ProductResource extends ProductServiceGrpc.ProductServiceImplBase{

    private final IProductService productService;

    public ProductResource(IProductService productService) {
        this.productService = productService;
    }

    @Override
    public void create(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        ProductInputDTO productInputDTO = new ProductInputDTO(
                request.getName(),
                request.getPrice(),
                request.getQuantityInStock()
        );

        ProductOutputDTO productOutputDTO = productService.create(productInputDTO);

        ProductResponse response = ProductResponse.newBuilder()
                .setId(productOutputDTO.getId())
                .setName(productOutputDTO.getName())
                .setPrice(productOutputDTO.getPrice())
                .setQuantityInStock(productOutputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void findById(RequestById request, StreamObserver<ProductResponse> responseObserver) {
        var productOutputDTO = productService.findById(request.getId());

        ProductResponse response = ProductResponse.newBuilder()
                .setId(productOutputDTO.getId())
                .setName(productOutputDTO.getName())
                .setPrice(productOutputDTO.getPrice())
                .setQuantityInStock(productOutputDTO.getQuantityInStock())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void delete(RequestById request, StreamObserver<EmptyResponse> responseObserver) {
        productService.delete(request.getId());

        responseObserver.onNext(EmptyResponse.newBuilder().build());
        responseObserver.onCompleted();
    }

    @Override
    public void findAll(EmptyRequest request, StreamObserver<ProductResponseList> responseObserver) {
        super.findAll(request, responseObserver);
    }

}
