package com.medicare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.model.Cart;
import com.medicare.model.OrderItem;
import com.medicare.model.Product;
import com.medicare.model.User;
import com.medicare.model.responses.BaseResponse;
import com.medicare.model.responses.CartResponse;
import com.medicare.service.ICartService;
import com.medicare.service.IOrderItemService;
import com.medicare.service.IProductService;
import com.medicare.service.IUserService;


@RestController()
@RequestMapping("/user/cart")
public class CartController {

	@Autowired
	private ICartService cartService;

	@Autowired
	private IOrderItemService orderItemService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IProductService productService;

	@PostMapping("/add/{productId}")
	public ResponseEntity<BaseResponse<Cart>> addCart(@RequestAttribute("userId") Long userId,
			@PathVariable Long productId) {
		BaseResponse<Cart> response = new BaseResponse<Cart>();

		if (productId <= 0) {
			response.setIsError(true);
			response.setMessage("Please select food item to add into cart");
			return ResponseEntity.badRequest().body(response);
		}

		User user = this.userService.getUserById(userId);

		Cart cart = user.getCart();
		if (cart == null) {
			cart = new Cart();
			cart.setUser(user);
			cart = this.cartService.saveCart(cart);
			if (cart.getCartId() > 0) {
				user.setCart(cart);
				user = this.userService.updateUser(user);
			}
		}
		List<OrderItem> orderItems = this.orderItemService.fetchOrderItemsByCart(cart.getCartId());

		int totalQuantity = 0;
		double totalCartPrice = 0.0;
		Product product = this.productService.getProductById(productId);
		boolean existingOrderItem = false;
		for (OrderItem orderItem : orderItems) {
			if (orderItem.getProduct().getProductId() == productId) {
				int quantity = orderItem.getQuantity() + 1;
				double price = orderItem.getProduct().getPrice() * quantity;
				orderItem.setQuantity(quantity);
				orderItem.setTotalPrice(price);
				this.orderItemService.saveOrderItem(orderItem);
				existingOrderItem = true;
			}
			totalQuantity = totalQuantity + orderItem.getQuantity();
			totalCartPrice = totalCartPrice + orderItem.getTotalPrice();
		}

		if (!existingOrderItem) {
			OrderItem orderItem = new OrderItem(0L, 1, product.getPrice(),product);
			orderItem.setCart(cart);
			OrderItem savedOrderItem = this.orderItemService.saveOrderItem(orderItem);
			totalQuantity = totalQuantity + savedOrderItem.getQuantity();
			totalCartPrice = totalCartPrice + savedOrderItem.getTotalPrice();
		}

		cart.setNoOfItems(totalQuantity);
		cart.setTotalCartPrice(totalCartPrice);

		Cart savedCart = this.cartService.saveCart(cart);
		response.setResponse(savedCart);

		response.setIsError(false);
		response.setMessage("Item added to cart");
		return ResponseEntity.ok(response);
	}

	@PostMapping("/update/{productId}/{quantity}")
	public ResponseEntity<BaseResponse<Cart>> updateCart(@RequestAttribute("userId") Long userId,
			@PathVariable Long productId, @PathVariable int quantity) {
		BaseResponse<Cart> response = new BaseResponse<Cart>();

		if (productId <= 0) {
			response.setIsError(true);
			response.setMessage("Please select food item to add into cart");
			return ResponseEntity.badRequest().body(response);
		}

		User user = this.userService.getUserById(userId);

		Cart cart = user.getCart();
		if (cart == null) {
			response.setIsError(true);
			response.setMessage("Could not update non-existing cart");
			return ResponseEntity.internalServerError().body(response);
		}
		List<OrderItem> orderItems = this.orderItemService.fetchOrderItemsByCart(cart.getCartId());

		int totalQuantity = 0;
		double totalCartPrice = 0.0;
		for (OrderItem orderItem : orderItems) {
			if (orderItem.getProduct().getProductId() == productId) {
				if (quantity > 0) {
					double price = orderItem.getProduct().getPrice() * quantity;
					orderItem.setQuantity(quantity);
					orderItem.setTotalPrice(price);
					this.orderItemService.saveOrderItem(orderItem);
				} else {
					this.orderItemService.deleteOrderItem(orderItem.getOrderItemId());
					continue;
				}
			}
			totalQuantity = totalQuantity + orderItem.getQuantity();
			totalCartPrice = totalCartPrice + orderItem.getTotalPrice();
		}

		cart.setNoOfItems(totalQuantity);
		cart.setTotalCartPrice(totalCartPrice);
		
		Cart savedCart = this.cartService.saveCart(cart);
		response.setResponse(savedCart);
		
		response.setIsError(false);
		response.setMessage("Cart items updated");
		return ResponseEntity.ok(response);
	}

	@GetMapping
	public ResponseEntity<BaseResponse<CartResponse>> getCart(@RequestAttribute("userId") Long userId) {
		BaseResponse<CartResponse> response = new BaseResponse<CartResponse>();

		User user = this.userService.getUserById(userId);

		if (user.getCart() != null) {
			Cart userCart = this.cartService.getCart(user.getCart().getCartId());
			response.setIsError(false);
			if (userCart == null) {
				response.setMessage("Empty cart");
			} else {
				List<OrderItem> orderItems = this.orderItemService.fetchOrderItemsByCart(userCart.getCartId());
				CartResponse cartResponse = new CartResponse(userCart, orderItems);
				response.setMessage("Cart fetched successfully!");
				response.setResponse(cartResponse);
			} 
		} else {
			response.setMessage("Empty cart");
		}
		return ResponseEntity.ok().body(response);
	}
}
