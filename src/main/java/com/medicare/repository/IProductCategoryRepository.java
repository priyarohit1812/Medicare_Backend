package com.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.medicare.model.ProductCategory;

public interface IProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	List<ProductCategory> findByCategoryNameContains(String categoryName);
	Optional<ProductCategory> findByCategoryName(String categoryName);
	
	@Query("SELECT DISTINCT(pc.categoryName) FROM ProductCategory pc")
	List<String> getDistictCategoryName();
}
