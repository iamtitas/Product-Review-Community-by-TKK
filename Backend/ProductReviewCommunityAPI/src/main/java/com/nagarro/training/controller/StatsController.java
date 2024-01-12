package com.nagarro.training.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.training.dto.StatsResponse;
import com.nagarro.training.service.StatsService;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "http://localhost:4200")
public class StatsController {

	private final StatsService statsService;

	@Autowired
	public StatsController(StatsService statsService) {
		this.statsService = statsService;
	}

	@GetMapping("/homepage")
	public ResponseEntity<StatsResponse> getHomePageStats() {
		int registeredUsersCount = statsService.getUserCount();
		int productsCount = statsService.getProductCount();
		int reviewsCount = statsService.getReviewCount();

		StatsResponse statsResponse = new StatsResponse(registeredUsersCount, productsCount, reviewsCount);

		return ResponseEntity.ok(statsResponse);
	}
}
