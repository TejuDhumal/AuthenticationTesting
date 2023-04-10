package com.javatechie.controller;

import com.javatechie.entity.Product;
import com.javatechie.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductService productService;


    @GetMapping("/welcome")
    public String welcome(){
        return "welcome page";
    }

    @PostMapping("/addProduct")
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public Product addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }

    @GetMapping("/getById/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + " || hasRole('ROLE_USER')")
    public ResponseEntity<Object> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        return ResponseEntity.ok(product);

    }

    @GetMapping("/allProduct")
    @PreAuthorize("hasRole('ROLE_USER')" + "|| hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_SUPERADMIN')")
    public List<Product> getAllProduct(){
        return productService.getProducts();
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product updateProduct(@RequestBody Product product)  {
        return productService.updateProduct(product);
    }


    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')" + "|| hasRole('ROLE_SUPERADMIN')")
    public String deleteById(@PathVariable int id){
        return productService.deleteById(id);
    }
}
