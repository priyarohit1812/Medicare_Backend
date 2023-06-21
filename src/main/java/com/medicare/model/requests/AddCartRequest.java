package com.medicare.model.requests;

public class AddCartRequest {
	
	private Long userId;
	private Long productId;
	
	public AddCartRequest(Long userId, Long productId) {
		this.userId = userId;
		this.productId = productId;
	}
	public AddCartRequest() {
		this(0L,0L);
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	
}
