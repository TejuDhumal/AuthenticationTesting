package com.javatechie.service;

import com.javatechie.entity.Product;
import com.javatechie.repository.ProductRepository;
import com.javatechie.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {



   @Autowired
    private ProductRepository productRepository;



    public List<Product>  getProducts() {
        return productRepository.findAll();
    }


    public Product addProduct(Product product) {
         return productRepository.save(product);
//         return "Product added successfully";

    }

    public Product getProductById(int id) {
       return productRepository.findById(id).orElseThrow(()-> new RuntimeException("product with "+id+" not found."));
    }

    public String deleteById(int id) {
        productRepository.deleteById(id);
        return ("successfully deleted " +id);
    }

    public Product updateProduct(Product product) {
        if (product == null){
            throw new RuntimeException() ;
        }
       return productRepository.save(product);

    }



}
