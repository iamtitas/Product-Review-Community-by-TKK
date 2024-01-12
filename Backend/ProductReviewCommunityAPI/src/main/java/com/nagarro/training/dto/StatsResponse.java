package com.nagarro.training.dto;

public class StatsResponse {
	private int userCount;
	private int productCount;
	private int reviewCount;

	public StatsResponse(int userCount, int productCount, int reviewCount) {
		this.userCount = userCount;
		this.productCount = productCount;
		this.reviewCount = reviewCount;
	}

	// Getters and setters

	public int getUserCount() {
		return userCount;
	}

	public void setUserCount(int userCount) {
		this.userCount = userCount;
	}

	public int getProductCount() {
		return productCount;
	}

	public void setProductCount(int productCount) {
		this.productCount = productCount;
	}

	public int getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
}
