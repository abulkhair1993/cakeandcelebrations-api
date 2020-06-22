package com.cakeandcelebration.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cakeandcelebration.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
