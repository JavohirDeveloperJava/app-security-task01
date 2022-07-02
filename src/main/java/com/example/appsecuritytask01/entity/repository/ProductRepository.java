package com.example.appsecuritytask01.entity.repository;

import com.example.appsecuritytask01.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Integer> {
}
