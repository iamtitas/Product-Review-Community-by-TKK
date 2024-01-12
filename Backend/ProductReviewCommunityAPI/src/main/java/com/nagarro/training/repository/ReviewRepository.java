package com.nagarro.training.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nagarro.training.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

	// function to find a review by productCode
	List<Review> findByProductProductCode(String code);

}
