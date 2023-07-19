package com.medicare.enums;

public enum PaymentType {
	CreditCard(0), CashOnDelivery(1);

	public final int type;
	
	private PaymentType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
}
