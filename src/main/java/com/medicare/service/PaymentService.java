package com.medicare.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.model.Payment;
import com.medicare.repository.IPaymentRepository;

@Service(value = "paymentService")
public class PaymentService implements IPaymentService {
	@Autowired
	private IPaymentRepository paymentRepository;

	@Override
	public Payment savePayment(Payment payment) {
		return this.paymentRepository.save(payment);
	}

	@Override
	public Payment getPaymentById(Long paymentId) {
		Optional<Payment> optPay = this.paymentRepository.findById(paymentId);
		if (optPay != null && optPay.isPresent()) {
			return optPay.get();
		}
		return null;
	}

}
