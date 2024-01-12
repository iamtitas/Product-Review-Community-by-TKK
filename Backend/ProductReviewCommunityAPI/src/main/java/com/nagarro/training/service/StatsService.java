package com.nagarro.training.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.training.repository.ProductRepository;
import com.nagarro.training.repository.ReviewRepository;
import com.nagarro.training.repository.UserRepository;

@Service
public class StatsService {

	private final UserRepository userRepository;
	private final ProductRepository productRepository;
	private final ReviewRepository reviewRepository;

	@Autowired
	public StatsService(UserRepository userRepository, ProductRepository productRepository,
			ReviewRepository reviewRepository) {
		this.userRepository = userRepository;
		this.productRepository = productRepository;
		this.reviewRepository = reviewRepository;
	}

	public int getUserCount() {
		return userRepository.findAll().size();
	}

	public int getProductCount() {
		return productRepository.findAll().size();
	}

	public int getReviewCount() {
		return reviewRepository.findAll().size();
	}
}
