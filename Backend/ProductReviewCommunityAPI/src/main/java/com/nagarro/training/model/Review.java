package com.nagarro.training.model;

import javax.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	private double rating;
	private String heading;
	private String review;

	// Constructors, getters, and setters

	public Review() {
	}

	public Review(Product product, double rating, String heading, String review) {
		this.product = product;
		this.rating = rating;
		this.heading = heading;
		this.review = review;
	}

	// Getters and Setters

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
}
