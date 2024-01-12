package com.nagarro.training.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	// function to find a product by productCode
	Optional<Product> findByProductCodeIgnoreCase(String productCode);

	// function to find a productId by productCode
	Optional<Product> findById(String productCode);

	// function to find a product by productName
	List<Product> findByProductNameIgnoreCase(String productName);

	// function to find a product by productBrand
	List<Product> findByBrandIgnoreCase(String brand);

	// function to check whether a product exists by its productCode
	boolean existsByProductCodeIgnoreCase(String productCode);

}
