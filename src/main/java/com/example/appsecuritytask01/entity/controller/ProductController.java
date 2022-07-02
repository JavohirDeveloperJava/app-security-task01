package com.example.appsecuritytask01.entity.controller;

import com.example.appsecuritytask01.entity.Product;
import com.example.appsecuritytask01.entity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/productt")
public class ProductController {
    @Autowired
    ProductRepository productRepository;


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PostMapping
    public HttpEntity<?> add(@RequestBody Product product){
        return ResponseEntity.ok(productRepository.save(product));
    }


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping
    public HttpEntity<?> get(){
        return ResponseEntity.ok(productRepository.findAll());
    }


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR')")
    @PutMapping("/{id}")
    public HttpEntity<?> put(@PathVariable Integer id,@RequestBody Product product){
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()){
            Product product1 = optionalProduct.get();
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            productRepository.save(product1);
            return ResponseEntity.ok(product1);
        }
        return ResponseEntity.notFound().build();
    }


    @PreAuthorize(value = "hasRole('SUPER_ADMIN')")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delet(@PathVariable Integer id){
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }


    @PreAuthorize(value = "hasAnyRole('SUPER_ADMIN','MODERATOR','OPERATOR')")
    @GetMapping("/{id}")
    public HttpEntity<?> getId(@PathVariable Integer id){
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity.status(optionalProduct.isPresent()?200:404).body(optionalProduct.orElse(null));
    }
}
