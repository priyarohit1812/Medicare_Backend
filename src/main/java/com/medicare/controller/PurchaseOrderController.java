package com.medicare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.model.Address;
import com.medicare.model.Cart;
import com.medicare.model.OrderItem;
import com.medicare.model.Payment;
import com.medicare.model.PurchaseOrder;
import com.medicare.model.User;
import com.medicare.model.requests.PurchaseOrderRequest;
import com.medicare.model.responses.BaseResponse;
import com.medicare.model.responses.PurchaseOrderResponse;
import com.medicare.service.IAddressService;
import com.medicare.service.IOrderItemService;
import com.medicare.service.IPaymentService;
import com.medicare.service.IPurchaseOrderService;
import com.medicare.service.IUserService;


@RestController()
@RequestMapping("/user/order")
public class PurchaseOrderController {
	@Autowired
	private IOrderItemService orderItemService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAddressService addressService;
	
	@Autowired
	private IPaymentService paymentService;

	@Autowired
	private IPurchaseOrderService purchaseOrderService;

	@PostMapping
	public ResponseEntity<BaseResponse<PurchaseOrderResponse>> placeOrder(@RequestAttribute("userId") Long userId,
			@RequestBody PurchaseOrderRequest purchaseOrderRequest) {
		BaseResponse<PurchaseOrderResponse> response = new BaseResponse<PurchaseOrderResponse>();

		User user = this.userService.getUserById(userId);

		Cart cart = user.getCart();
		if (cart == null) {
			response.setIsError(true);
			response.setMessage("Could not place order on non-existing cart");
			return ResponseEntity.internalServerError().body(response);
		}
		List<OrderItem> orderItems = this.orderItemService.fetchOrderItemsByCart(cart.getCartId());
		if (purchaseOrderRequest.getAddress().getAddressId() <= 0) {
			purchaseOrderRequest.getAddress().setUser(user);
			Address savedAddress = this.addressService.saveAddress(purchaseOrderRequest.getAddress());
			if (savedAddress != null && savedAddress.getAddressId() >= 0) {
				purchaseOrderRequest.setAddress(savedAddress);
			}
		}
		if (purchaseOrderRequest.getPayment().getPaymentId() <= 0) {
			Payment savedPayment = this.paymentService.savePayment(purchaseOrderRequest.getPayment());
			if (savedPayment != null && savedPayment.getPaymentId() >= 0) {
				purchaseOrderRequest.setPayment(savedPayment);
			}
		}
		PurchaseOrder purchaseOrder = new PurchaseOrder(user,cart,
				purchaseOrderRequest.getAddress(), purchaseOrderRequest.getPayment());
		purchaseOrder = this.purchaseOrderService.savePurchaseOrder(purchaseOrder);

		if (purchaseOrder.getPurchaseOrderId() <= 0) {
			response.setIsError(true);
			response.setMessage("Could not place the order");
			return ResponseEntity.internalServerError().body(response);
		} else {
			user.setCart(null);
			this.userService.saveUser(user);
			PurchaseOrderResponse orderResponse = new PurchaseOrderResponse(purchaseOrder, orderItems);
			response.setIsError(false);
			response.setMessage("Order placed successfully!");
			response.setResponse(orderResponse);
			return ResponseEntity.ok(response);
		}
	}

	@GetMapping("/{orderId}")
	public ResponseEntity<BaseResponse<PurchaseOrderResponse>> getUserPurchaseOrder(@RequestAttribute("userId") Long userId,
			@PathVariable Long orderId) {
		BaseResponse<PurchaseOrderResponse> response = new BaseResponse<PurchaseOrderResponse>();

		if (orderId <= 0) {
			response.setIsError(true);
			response.setMessage("Please provide order number/id");
			return ResponseEntity.badRequest().body(response);
		}

		PurchaseOrder order = this.purchaseOrderService.getPurchaseorder(orderId);
		List<OrderItem> orderItems = this.orderItemService.fetchOrderItemsByCart(order.getCart().getCartId());

		PurchaseOrderResponse orderResponse = new PurchaseOrderResponse(order, orderItems);
		response.setIsError(false);
		response.setMessage("Order fetched successfully!");
		response.setResponse(orderResponse);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/list")
	public ResponseEntity<BaseResponse<List<PurchaseOrder>>> getUserPurchaseOrders(@RequestAttribute("userId") Long userId) {
		BaseResponse<List<PurchaseOrder>> response = new BaseResponse<List<PurchaseOrder>>();

		List<PurchaseOrder> orders = this.purchaseOrderService.getAllPurchaseOrderByUserId(userId);

		response.setIsError(false);
		response.setMessage("Orders fetched successfully!");
		response.setResponse(orders);
		return ResponseEntity.ok(response);
	}	
}
