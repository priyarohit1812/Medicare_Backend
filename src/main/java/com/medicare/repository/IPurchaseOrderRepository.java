package com.medicare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicare.model.PurchaseOrder;

public interface IPurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
	@Query(nativeQuery = true, value = "SELECT po.* FROM mc_purchaseorder po JOIN mc_cart c ON c.cart_id = po.cart_id_fk JOIN mc_user u ON u.user_id = c.user_id_fk WHERE u.user_id = :userId ORDER BY po.order_date DESC")
	List<PurchaseOrder> getOrdersByUser(@Param("userId") Long userId);
}
