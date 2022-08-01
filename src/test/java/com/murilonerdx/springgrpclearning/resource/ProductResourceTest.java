package com.murilonerdx.springgrpclearning.resource;

import com.murilonerdx.springgrpclearning.ProductRequest;
import com.murilonerdx.springgrpclearning.ProductResponse;
import com.murilonerdx.springgrpclearning.ProductServiceGrpc;
import com.murilonerdx.springgrpclearning.exception.ProductAlreadyExistsException;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.assertj.core.api.Assertions;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest()
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext
public class ProductResourceTest {

    @GrpcClient("inProcess")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceGrpc;

    private Flyway flyWay;

    @BeforeEach
    public void setUp(){
        flyWay.clean();
        flyWay.migrate();
    }

    @Test
    @DisplayName("when valid data is provided a product is create")
    public void createProductSuccessTest() {

        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("product name")
                .setPrice(10.00)
                .setQuantityInStock(6)
                .build();

        ProductResponse productResponse = productServiceGrpc.create(productRequest);

        Assertions.assertThat(productRequest)
                .usingRecursiveComparison()
                .comparingOnlyFields("name", "price", "quantity_in_stock")
                .isEqualTo(productResponse);
    }

    @Test
    @DisplayName("when create is called with duplicated name, throw ProductAlreadyExistsException")
    public void createProductAlreadyExistsExceptionTest() {
        ProductRequest productRequest = ProductRequest.newBuilder()
                .setName("Product A")
                .setPrice(10.00)
                .setQuantityInStock(100)
                .build();

        assertThatExceptionOfType(StatusRuntimeException.class)
                .isThrownBy(() -> productServiceGrpc.create(productRequest))
                .withMessage("ALREADY_EXISTS: Produto Product A já cadastrado no sistema");

    }

}
