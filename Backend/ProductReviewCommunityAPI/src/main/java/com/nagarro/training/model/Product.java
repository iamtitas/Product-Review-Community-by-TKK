package com.nagarro.training.model;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String brand;

	@Column(nullable = false)
	private String productName;

	@Column(nullable = false, unique = true)
	private String productCode;

	@Column(nullable = false)
	private double price;

	// Constructors, getters, and setters

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Product() {
	}

	public Product(String productCode) {
		this.productCode = productCode;
	}

	public Product(String brand, String productName, String productCode, double price) {
		this.brand = brand;
		this.productName = productName;
		this.productCode = productCode;
		this.price = price;
	}

	// Getters and Setters

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
