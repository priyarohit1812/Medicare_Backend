package com.medicare.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mc_product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@NotBlank(message = "product code is mandatory")
	private String productCode;
	@NotBlank(message = "Product name is mandatory")
	private String productName;
	@NotBlank(message = "Brand is mandatory")
	private String brand;
	@NotNull(message = "Manufacturing Date is mandatory")
	private String mfgDate;
	@NotNull(message = "Expiery Date is mandatory")
	private String expDate;
	@NotNull(message = "Seller is mandatory")
	private String seller;
	@NotNull(message = "Discription is mandatory")
	private String discription;
	@NotNull(message = "Price is mandatory")
	private double price;

	@OneToMany(mappedBy = "product", cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	@JsonIgnoreProperties("product")
	private Set<Image> images = new HashSet<>();

	@ManyToOne
	@JoinColumn(name = "productCategory_id", nullable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "products" })
	private ProductCategory productCategory;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMfgDate() {
		return mfgDate;
	}

	public void setMfgDate(String mfgDate) {
		this.mfgDate = mfgDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	public String getDiscription() {
		return discription;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProductCategory getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(ProductCategory productCategory) {
		this.productCategory = productCategory;
	}

	public Product(Long productId, String productCode, String productName, String brand, String mfgDate,
			String expDate, String seller, String discription, double price) {
		this.productId = productId;
		this.productCode = productCode;
		this.productName = productName;
		this.brand = brand;
		this.mfgDate = mfgDate;
		this.expDate = expDate;
		this.seller = seller;
		this.discription = discription;
		this.price = price;

	}

	public Product() {
		this(0L, "", "", "", "", "", "", "", 0.0);
	}

	@Override
	public String toString() {
		return "Product [productId=" + productId + ", productCode=" + productCode + ", productName=" + productName
				+ ", brand=" + brand + ",  mfgDate=" + mfgDate + ", expDate=" + expDate
				+ ", seller=" + seller + ", discription=" + discription + ", price="
				+ price + ", productCategory=" + productCategory + "]";
	}

}
