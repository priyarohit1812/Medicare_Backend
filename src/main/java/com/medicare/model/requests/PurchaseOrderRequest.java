package com.medicare.model.requests;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medicare.model.Address;
import com.medicare.model.Payment;

public class PurchaseOrderRequest {
	@JsonIgnoreProperties("user")
	private Address address;
	private Payment payment;
	
	public PurchaseOrderRequest(Address address, Payment payment) {
		this.address = address;
		this.payment = payment;
	}
	
	public PurchaseOrderRequest() {
		this(null, null);
	}
	
	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
