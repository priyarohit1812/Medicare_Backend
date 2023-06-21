package com.medicare.service;

import java.util.List;

import com.medicare.model.PurchaseOrder;

public interface IPurchaseOrderService {
	public List<PurchaseOrder> fetchPurchaseOrderList();
	public List<PurchaseOrder> getAllPurchaseOrderByAddressId(Long addressId);
	public List<PurchaseOrder> getAllPurchaseOrderByUserId(Long userId);
	public PurchaseOrder getPurchaseorder(Long purchaseOrderId);
	public PurchaseOrder savePurchaseOrder(PurchaseOrder purchaseOrder);
	boolean deletePurchaseOrder(Long purchaseOrderId);
	
}
