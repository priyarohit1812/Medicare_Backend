package com.medicare.service;

import com.medicare.model.Payment;

public interface IPaymentService {
	public Payment savePayment(Payment payment);
	public Payment getPaymentById(Long paymentId);
	
}
