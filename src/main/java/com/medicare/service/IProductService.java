package com.medicare.service;

import java.util.List;
import java.util.Set;

import com.medicare.model.Image;
import com.medicare.model.Product;

public interface IProductService {
	public List<Product> fetchProductList();
	public List<Product> fetchProductListByBrand(String brand);
	public List<Product> searchProduct(String key);
	public Product getProductById(Long productId);
	public Product saveProduct(Product product);
	public boolean deleteProduct(Long productId);
	public List<String> getBrands();
	public List<Product> getProductByProductCategory(Long productCategoryId);
	public Set<Image> getProductImages(Long productId);
}
