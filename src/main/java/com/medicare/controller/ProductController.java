package com.medicare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.model.Product;
import com.medicare.model.responses.BaseResponse;
import com.medicare.service.IProductService;


@RestController()
@RequestMapping("/user/product")
public class ProductController {
	
	@Autowired
	private IProductService productService;
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse<List<Product>>> getAllProducts(){
		BaseResponse<List<Product>> response = new BaseResponse<List<Product>>();
		try {
			List<Product> allProducts= this.productService.fetchProductList();
			if (allProducts == null || allProducts.isEmpty() ) {
				response.setMessage("No product found");
			}
			
			response.setIsError(false);
			response.setResponse(allProducts);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setIsError(true);
			response.setMessage("Could not fetch Products." + e.getMessage());
			response.setResponse(null);
			return ResponseEntity.internalServerError().body(response);
		}
			
	}	
		
	@GetMapping("/{productId}")
	public ResponseEntity<BaseResponse<Product>> getproduct(@PathVariable Long productId){
		BaseResponse<Product> response = new BaseResponse<Product>();
		if (productId<=0) {
			response.setIsError(true);
			response.setMessage("Please provide product id to fetch product");
			return ResponseEntity.badRequest().body(response);
		}
		
		Product product = this.productService.getProductById(productId);
		if (product==null) {
			response.setIsError(true);
			response.setMessage("Could not fetch the requested product");
			return ResponseEntity.internalServerError().body(response);
		}
		
		response.setIsError(false);
		response.setMessage("Product found!");
		response.setResponse(product);
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/list/{productCategoryId}")
	public ResponseEntity<BaseResponse<List<Product>>> getAllProducts(@PathVariable Long productCategoryId) {
		BaseResponse<List<Product>> response = new BaseResponse<List<Product>>();
		try {
			if (productCategoryId <= 0) {
				response.setIsError(true);
				response.setMessage("Please provide food category id to fetch results");
				return ResponseEntity.badRequest().body(response);
			}
			List<Product> allProducts = this.productService.getProductByProductCategory(productCategoryId);
			if (allProducts == null || allProducts.isEmpty()) {
				response.setMessage("No result found");
			}
			response.setIsError(false);
			response.setResponse(allProducts);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setIsError(true);
			response.setMessage("Could not fetch products. " + e.getMessage());
			response.setResponse(null);
			return ResponseEntity.internalServerError().body(response);
		}
	}
	
	@GetMapping("/searchByBrand/{key}")
	public ResponseEntity<BaseResponse<List<Product>>>searchProductByBrand(@PathVariable String key){
		BaseResponse<List<Product>> response = new BaseResponse<List<Product>>();
		try {
			if (key.isBlank()) {
				response.setIsError(true);
				response.setMessage("Please provide brand name to fetch results");
				return ResponseEntity.badRequest().body(response);
			}
			List<Product> productByBrand = this.productService.fetchProductListByBrand(key);
			
			if (productByBrand == null || productByBrand.isEmpty()) {
				response.setMessage("No result found");
			}
			response.setIsError(false);
			response.setResponse(productByBrand);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setIsError(true);
			response.setMessage("Could not fetch products. " + e.getMessage());
			response.setResponse(null);
			return ResponseEntity.internalServerError().body(response);
		}
		
	}
	
	@GetMapping("/search/{key}")
	public ResponseEntity<BaseResponse<List<Product>>>searchProduct(@PathVariable String key){
		BaseResponse<List<Product>> response = new BaseResponse<List<Product>>();
		try {
			if (key.isBlank()) {
				response.setIsError(true);
				response.setMessage("Please provide key to fetch results");
				return ResponseEntity.badRequest().body(response);
			}
			List<Product> products = this.productService.searchProduct(key);
			
			if (products == null || products.isEmpty()) {
				response.setMessage("No result found");
			}
			response.setIsError(false);
			response.setResponse(products);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setIsError(true);
			response.setMessage("Could not fetch products. " + e.getMessage());
			response.setResponse(null);
			return ResponseEntity.internalServerError().body(response);
		}
		
	}	
}
