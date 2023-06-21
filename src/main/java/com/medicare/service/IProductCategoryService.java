package com.medicare.service;

import java.util.List;

import com.medicare.model.ProductCategory;

public interface IProductCategoryService {
	public List<ProductCategory> fetchProductCategoryList();
	public List<ProductCategory> searchProductCategory(String key);
	public ProductCategory searchProductCategoryByName(String key);
	public ProductCategory saveProductCategory(ProductCategory productCategory);
	boolean deleteProductCategory(Long productCategoryId);
	public ProductCategory getProductCategoryById(Long productCategoryId);
	public List<String> getCategoryNames();
	
}
