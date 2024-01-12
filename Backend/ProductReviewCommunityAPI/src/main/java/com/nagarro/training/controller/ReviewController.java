package com.nagarro.training.controller;

import com.nagarro.training.model.Product;
import com.nagarro.training.model.Review;
import com.nagarro.training.repository.ProductRepository;
import com.nagarro.training.repository.ReviewRepository;
import com.nagarro.training.service.ReviewService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {

	private final ReviewRepository reviewRepository;
	private final ReviewService reviewService;
	private final ProductRepository productRepository;

	@Autowired
	public ReviewController(ReviewRepository reviewRepository, ReviewService reviewService,
			ProductRepository productRepository) {
		this.reviewRepository = reviewRepository;
		this.reviewService = reviewService;
		this.productRepository = productRepository;
	}

	@GetMapping("/reviews/{code}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<List<Review>> getReviewByCode(@PathVariable String code) {
		List<Review> reviews = reviewService.getReviewByCode(code);
		if (!reviews.isEmpty()) {
			return ResponseEntity.ok(reviews);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/reviews/average-rating/{code}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Double> getAverageRatingByCode(@PathVariable String code) {
		double averageRating = reviewService.getAverageRatingByCode(code);
		return ResponseEntity.ok(averageRating);
	}

	@GetMapping("/reviews/total-reviews/{code}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Integer> getTotalReviewsByCode(@PathVariable String code) {
		int totalReviews = reviewService.getTotalReviewsByCode(code);
		return ResponseEntity.ok(totalReviews);
	}

	@PostMapping("/reviews/{code}")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Review> postReview(@PathVariable String code, @RequestBody ReviewRequest reviewRequest) {
		try {
			// Find the product entity by product code
			Optional<Product> optionalProduct = productRepository.findByProductCodeIgnoreCase(code);
			if (optionalProduct.isPresent()) {
				Product product = optionalProduct.get();

				// Create a new Review entity with the provided data
				Review review = new Review();
				review.setProduct(product);
				review.setRating(reviewRequest.getRating());
				review.setHeading(reviewRequest.getHeading());
				review.setReview(reviewRequest.getReviewText());

				// Save the review to the database
				reviewRepository.save(review);

				return ResponseEntity.ok(review);
			} else {
				// Product not found for the given product code
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	static class ReviewRequest {
		private String productCode;
		private double rating;
		private String heading;
		private String reviewText;

		// Getters and setters

		public String getProductCode() {
			return productCode;
		}

		public void setProductCode(String productCode) {
			this.productCode = productCode;
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

		public String getReviewText() {
			return reviewText;
		}

		public void setReviewText(String reviewText) {
			this.reviewText = reviewText;
		}
	}
}
