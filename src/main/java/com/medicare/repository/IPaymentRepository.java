package com.medicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.model.Payment;

public interface IPaymentRepository extends JpaRepository<Payment, Long> {

}
