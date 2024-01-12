package com.nagarro.training.service;

import java.util.List;
import java.util.Optional;
import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.model.Product;
import com.nagarro.training.model.Review;
import com.nagarro.training.repository.ProductRepository;
import com.nagarro.training.repository.ReviewRepository;

@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductRepository productRepository;

	@Autowired
	public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.productRepository = productRepository;
	}

	public Review postReview(String productCode, double rating, String heading, String reviewText) {
		// Check if the product exists
		Product product = getProductByCode(productCode);

		// Create a new review object
		Review review = new Review(product, rating, heading, reviewText);

		// Save the review to the database
		return reviewRepository.save(review);
	}

	private Product getProductByCode(String productCode) {
		Optional<Product> productOptional = productRepository.findByProductCodeIgnoreCase(productCode);
		return productOptional.orElseThrow(() -> new IllegalArgumentException("Product not found"));
	}

	public List<Review> getReviewByCode(String code) {
		return reviewRepository.findByProductProductCode(code);
	}

	public double getAverageRatingByCode(String code) {
		List<Review> reviews = reviewRepository.findByProductProductCode(code);
		int totalReviews = reviews.size();

		if (totalReviews == 0) {
			return 0.0; // No reviews, return 0 as average rating
		}

		double sum = 0.0;
		for (Review review : reviews) {
			sum += review.getRating();
		}

		double averageRating = sum / totalReviews;

		// Format the average rating to display up to 1 decimal point
		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		return Double.parseDouble(decimalFormat.format(averageRating));
	}

	public int getTotalReviewsByCode(String code) {
		List<Review> reviews = reviewRepository.findByProductProductCode(code);
		return reviews.size();
	}

}
