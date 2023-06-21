package com.medicare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.model.ProductCategory;
import com.medicare.repository.IProductCategoryRepository;

@Service(value = "productCategoryService")
public class ProductCategoryService implements IProductCategoryService {
	@Autowired
	private IProductCategoryRepository productCategoryRepository;
	
	@Override
	public List<ProductCategory> fetchProductCategoryList() {
		return this.productCategoryRepository.findAll();
	}

	@Override
	public ProductCategory saveProductCategory(ProductCategory productCategory) {
		return this.productCategoryRepository.save(productCategory);
	}

	@Override
	public boolean deleteProductCategory(Long productCategoryId) {
		this.productCategoryRepository.deleteById(productCategoryId);
		return !this.productCategoryRepository.existsById(productCategoryId) ;
	}

	@Override
	public ProductCategory getProductCategoryById(Long productCategoryId) {
		Optional<ProductCategory> optProductCategory = this.productCategoryRepository.findById(productCategoryId);
		if (optProductCategory !=null && optProductCategory.isPresent()) {
			return optProductCategory.get();
		}else {
			return null;
		}
	}

	@Override
	public List<ProductCategory> searchProductCategory(String key) {
		return this.productCategoryRepository.findByCategoryNameContains(key);
	}

	@Override
	public ProductCategory searchProductCategoryByName(String key) {
		Optional<ProductCategory> optPC = this.productCategoryRepository.findByCategoryName(key);
		if (optPC!=null && optPC.isPresent()) {
			return optPC.get();
		}
		return null;
	}

	
	@Override
	public List<String> getCategoryNames() {
		return this.productCategoryRepository.getDistictCategoryName();
	}
}
