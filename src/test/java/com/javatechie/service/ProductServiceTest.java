package com.javatechie.service;

import com.javatechie.entity.Product;
import com.javatechie.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productservice;

    @BeforeEach
    void setUp(){

    }


    @Test
    void getProducts() {
        List<Product> expectedProducts = Arrays.asList(
                new Product(1, "Test Product 1", 10 , 100.3),
                new Product(2, "Test Product 2", 20 , 239.3),
                new Product(3, "Test Product 3", 30 , 354.2)
        );
        when(productRepository.findAll()).thenReturn(expectedProducts);

        // act
        List<Product> actualProducts = productservice.getProducts();

        // assert
        assertEquals(expectedProducts, actualProducts);
        verify(productRepository).findAll();
    }

    @Test
    void addProduct() {

        Product dummyProduct = new Product();
        dummyProduct.setProductId(1);
        dummyProduct.setName("Book");
        dummyProduct.setQty(100);
        dummyProduct.setPrice(10.2);

        productservice.addProduct(dummyProduct);

        // eq() is from the Mockito matchers package
        Mockito.verify(productRepository, Mockito.times(1)).save(eq(dummyProduct));

    }

    @Test
    void getProductById() {
        Product expectedProduct = new Product();
        expectedProduct.setProductId(1);
        expectedProduct.setName("Book");
        expectedProduct.setQty(100);
        expectedProduct.setPrice(20.1);
        when(productRepository.findById(anyInt())).thenReturn(Optional.of(expectedProduct));
        Product responseEntity = productservice.getProductById(2);
//        System.out.println(actualProduct);
//        assertThat(expectedProduct).isEqualTo(actualProduct);
        assertAll(
                "proprties",
                ()->assertEquals("Book",responseEntity.getName()),
                ()->assertEquals(20.1,responseEntity.getPrice()),
                ()->assertEquals(100,responseEntity.getQty())
        );
    }

    @Test
    void deleteById() {
        Product product = new Product();
        product.setProductId(1);
        product.setName("Book");
        product.setPrice(12.5);
        product.setQty(200);
        int id = 1;
        String expectedMessage = "successfully deleted " + id;

        String actualMessage = productservice.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
        assertEquals(expectedMessage, actualMessage);

    }

    @Test
    void updateProduct() {
        Product dummyProduct = new Product(8,"laptop",20,12.3);

        productservice.addProduct(dummyProduct);

        // eq() is from the Mockito matchers package
        Mockito.verify(productRepository, Mockito.times(1)).save(eq(dummyProduct));




    }
}