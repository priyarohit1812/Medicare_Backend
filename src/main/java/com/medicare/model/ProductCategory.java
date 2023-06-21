package com.medicare.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "mc_productcategory")
public class ProductCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productcategoryId;
	@NotBlank(message = "Category name is mandatory")
	private String categoryName;

	@OneToMany(mappedBy = "productCategory", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JsonIgnoreProperties("productCategory")
	private Set<Product> products = new HashSet<>();

	public Long getProductcategoryId() {
		return productcategoryId;
	}

	public void setProductcategoryId(Long productcategoryId) {
		this.productcategoryId = productcategoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Set<Product> getProducts() {
		return products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public ProductCategory(Long productcategoryId, String categoryName) {
		this.productcategoryId = productcategoryId;
		this.categoryName = categoryName;

	}

	public ProductCategory() {
		this(0L, "");
	}

	@Override
	public String toString() {
		return "ProductCategory [productcategoryId=" + productcategoryId + ", categoryName=" + categoryName
				+ ", products=" + products + "]";
	}

	
}
