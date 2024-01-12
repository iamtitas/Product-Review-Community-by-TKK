package com.nagarro.training.controller;

import com.nagarro.training.model.Product;
import com.nagarro.training.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

	private final ProductService productService;

	@Autowired
	public ProductController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public ResponseEntity<List<Product>> getAllProducts() {
		List<Product> products = productService.getAllProducts();
		return ResponseEntity.ok(products);
	}

	@GetMapping("/products/code/{code}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Product> getProductByCode(@PathVariable String code) {
		Optional<Product> optionalProduct = productService.getProductByCode(code);
		if (optionalProduct.isPresent()) {
			Product product = optionalProduct.get();
			return ResponseEntity.ok(product);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/products/name/{name}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
		List<Product> products = productService.getProductByName(name);
		if (!products.isEmpty()) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@GetMapping("/products/brand/{brand}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Product>> getProductsByBrand(@PathVariable String brand) {
		List<Product> products = productService.getProductsByBrand(brand);
		if (!products.isEmpty()) {
			return ResponseEntity.ok(products);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
	}

	@PostMapping("/products")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> saveProduct(@RequestBody Product product) {
		boolean exists = productService.existsProductByCode(product.getProductCode());
		if (exists) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Product with the same code already exists");
		} else {
			productService.saveProduct(product);
			return ResponseEntity.status(HttpStatus.CREATED).body("Product saved successfully");
		}
	}

	static class ProductRequest {
		private String name;
		private String code;
		private String brand;
		private double price;

		// Getters and setters

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}
	}
}