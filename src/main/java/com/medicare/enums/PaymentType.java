package com.medicare.enums;

public enum PaymentType {
	CreditCard(1), DebitCard(2), CashOnDelivery(3);

	public final int type;
	
	private PaymentType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}
	
}
