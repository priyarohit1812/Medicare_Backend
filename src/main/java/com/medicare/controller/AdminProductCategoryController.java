package com.medicare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.medicare.model.ProductCategory;
import com.medicare.model.responses.BaseResponse;
import com.medicare.service.IProductCategoryService;

@RestController()
@RequestMapping("/admin/productcategory")
public class AdminProductCategoryController {
	
	@Autowired
	private IProductCategoryService productCategoryService;
	
	@GetMapping("/list")
	public ResponseEntity<BaseResponse<List<ProductCategory>>> getAllProductCategories(){
		BaseResponse<List<ProductCategory>> response = new BaseResponse<List<ProductCategory>>();
		try {
			List<ProductCategory> allProductCategories= this.productCategoryService.fetchProductCategoryList();
			if (allProductCategories == null || allProductCategories.isEmpty() ) {
				response.setMessage("No productCategory found");
			}
			
			response.setIsError(false);
			response.setResponse(allProductCategories);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.setIsError(true);
			response.setMessage("Could not fetch ProductCategories." + e.getMessage());
			response.setResponse(null);
			return ResponseEntity.internalServerError().body(response);
		}
			
	}
	
	@PostMapping("/save")
	public ResponseEntity<BaseResponse<ProductCategory>> saveProductCategory(@RequestBody ProductCategory productCategory){
		BaseResponse<ProductCategory> response = new BaseResponse<ProductCategory>();
		if (productCategory==null) {
			response.setIsError(true);
			response.setMessage("Request object is null");
			return ResponseEntity.badRequest().body(response);
		}
		
		ProductCategory savedProductCategory = this.productCategoryService.saveProductCategory(productCategory);
		if (savedProductCategory == null) {
			response.setIsError(true);
			response.setMessage("Could not save the requested productCategory");
			return ResponseEntity.internalServerError().body(response);
			
		}
		
		response.setIsError(false);
		response.setMessage("ProductCategory saved successfully!");
		response.setResponse(savedProductCategory);
		return ResponseEntity.ok(response);
		
	}
	
		
	@GetMapping("/{productCategoryId}")
	public ResponseEntity<BaseResponse<ProductCategory>> getproductCategory(@PathVariable Long productCategoryId){
		BaseResponse<ProductCategory> response = new BaseResponse<ProductCategory>();
		if (productCategoryId<=0) {
			response.setIsError(true);
			response.setMessage("Please provide productCategory id to fetch productCategory");
			return ResponseEntity.badRequest().body(response);
		}
		
		ProductCategory productCategory = this.productCategoryService.getProductCategoryById(productCategoryId);
		if (productCategory==null) {
			response.setIsError(true);
			response.setMessage("Could not fetch the requested productCategory");
			return ResponseEntity.internalServerError().body(response);
		}
		
		response.setIsError(false);
		response.setMessage("ProductCategory found!");
		response.setResponse(productCategory);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{productCategoryId}")
	public ResponseEntity<BaseResponse<?>> deleteProductCategory(@PathVariable Long productCategoryId ){
		BaseResponse<ProductCategory> response = new BaseResponse<ProductCategory>();
		if (productCategoryId<=0) {
			response.setIsError(true);
			response.setMessage("Please provide ProductCategory id to fetch ProductCategory");
			return ResponseEntity.badRequest().body(response);
		}
		
		boolean isDeleted = this.productCategoryService.deleteProductCategory(productCategoryId);
		if (isDeleted) {
			response.setIsError(true);
			response.setMessage("ProductCategory deleted successfully!");
			return ResponseEntity.ok(response);
		}else {
			response.setIsError(true);
			response.setMessage("Could not delete the requested productCategory");
			return ResponseEntity.internalServerError().body(response);
		}
		
	}
	
}
