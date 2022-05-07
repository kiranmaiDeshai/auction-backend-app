package com.wellsfargo.training.test.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wellsfargo.training.test.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
