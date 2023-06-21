package com.medicare.service;

import java.util.List;

import com.medicare.model.Cart;

public interface ICartService {
	public List<Cart> fetchCartList(); 
	public Cart saveCart(Cart cart);
	boolean deleteCart(Long cartId);
	public Cart getCart(Long cartId);
}
