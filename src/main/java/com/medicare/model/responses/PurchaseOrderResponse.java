package com.medicare.model.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicare.model.OrderItem;
import com.medicare.model.PurchaseOrder;

public class PurchaseOrderResponse {
	private PurchaseOrder purchaseOrder;
	@JsonIgnoreProperties("cart")
	private List<OrderItem> orderItems;

	public PurchaseOrderResponse() {
		this(null,null);
	}

	public PurchaseOrderResponse(PurchaseOrder purchaseOrder, List<OrderItem> orderItems) {
		this.purchaseOrder = purchaseOrder;
		this.orderItems = orderItems;
	}

	public PurchaseOrder getOrder() {
		return purchaseOrder;
	}

	public void setOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public List<OrderItem> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
}
