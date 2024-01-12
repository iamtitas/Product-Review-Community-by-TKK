package com.nagarro.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.model.Product;
import com.nagarro.training.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	@Autowired
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	public Optional<Product> getProductByCode(String code) {
		return productRepository.findByProductCodeIgnoreCase(code);
	}

	public List<Product> getProductsByBrand(String brand) {
		return productRepository.findByBrandIgnoreCase(brand);
	}

	public List<Product> getProductByName(String name) {
		return productRepository.findByProductNameIgnoreCase(name);
	}

	public boolean existsProductByCode(String code) {
		return productRepository.existsByProductCodeIgnoreCase(code);
	}

	public void saveProduct(Product product) {
		productRepository.save(product);
	}

}
