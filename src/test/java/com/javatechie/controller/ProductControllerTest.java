package com.javatechie.controller;

import com.javatechie.entity.Product;
import com.javatechie.repository.ProductRepository;
import com.javatechie.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

   @InjectMocks
    private ProductController productController;

    @Test
    void addProduct() {
        Product dummyProduct = new Product(8,"laptop",20,12.3);

        productService.addProduct(dummyProduct);

        Mockito.verify(productService, Mockito.times(1)).addProduct(eq(dummyProduct));
    }

    @Test
    void getAllProduct() {
        List<Product> expectedProducts = Arrays.asList(
                new Product(1, "Test Product 1", 10 , 100.3),
                new Product(2, "Test Product 2", 20 , 239.3),
                new Product(3, "Test Product 3", 30 , 354.2)
        );
        when(productService.getProducts()).thenReturn(expectedProducts);

        // act
        List<Product> actualProducts = productController.getAllProduct();

        // assert
        assertEquals(expectedProducts, actualProducts);
        verify(productService).getProducts();
    }

    @Test
    void updateProduct() {
        Product dummyProduct = new Product(8,"laptop",20,12.3);

        productService.addProduct(dummyProduct);

        Mockito.verify(productService, Mockito.times(1)).addProduct(eq(dummyProduct));

    }


}