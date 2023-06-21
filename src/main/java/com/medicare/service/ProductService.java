package com.medicare.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.model.Image;
import com.medicare.model.Product;
import com.medicare.repository.IProductRepository;

@Service(value ="productService")
public class ProductService implements IProductService {
	@Autowired
	private IProductRepository productRepository;
	
	
	@Override
	public List<Product> fetchProductList() {
		return this.productRepository.findAll();
	}

	@Override
	public Product getProductById(Long productId) {
		Optional<Product> optProduct = this.productRepository.findById(productId);
		if (optProduct !=null && optProduct.isPresent()) {
			return optProduct.get();
		}else {
			return null;
		}
	}

	@Override
	public Product saveProduct(Product product) {
		return this.productRepository.save(product);
	}

	@Override
	public boolean deleteProduct(Long productId) {
		this.productRepository.deleteById(productId);
		return !this.productRepository.existsById(productId);
	}

	@Override
	public List<Product> searchProduct(String key) {
		return this.productRepository.findByProductNameContainsOrBrandContains(key, key);
	}

	@Override
	public List<String> getBrands() {
		return this.productRepository.getDistictBrand();
	}

	@Override
	public List<Product> fetchProductListByBrand(String brand) {
		return this.productRepository.findByBrand(brand);
	}

	@Override
	public List<Product> getProductByProductCategory(Long productCategoryId) {
		return this.productRepository.getProductByProductCategory(productCategoryId);
	}

	@Override
	public Set<Image> getProductImages(Long productId) {
		
		Optional<Product> product = productRepository.findById(productId);
				 return product.get().getImages();
	}
	
	
	
	

	/*@Override
	public List<Product> getAllProductByCartId(Long cartId) {
		return this.productRepository.findAllProductBycartId(cartId);
	}

	@Override
	public List<Product> getAllProductByProductCategory(ProductCategory productCategoryId) {
		return this.productRepository.findAllProductByProductCategory(productCategoryId);
	}*/

}
