package com.medicare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicare.model.Product;

public interface IProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByProductNameContainsOrBrandContains(String productName, String brand);
	List<Product> findByBrand(String brand);
	
	@Query("SELECT DISTINCT(p.brand) FROM Product p")
	List<String> getDistictBrand();
	
	@Query(nativeQuery = true, value = "SELECT foo.* FROM mc_product foo WHERE foo.product_category_id = :productCategoryId")
	List<Product> getProductByProductCategory(@Param("productCategoryId") Long productCategoryId);
}
