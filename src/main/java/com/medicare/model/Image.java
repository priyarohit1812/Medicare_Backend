package com.medicare.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "mc_image")
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long imageId;
	@NotNull(message = "Image_url is mandatory")
	private String image_url;

	@ManyToOne
	@JoinColumn(name = "product_id", nullable = true)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "images" })
	private Product product;

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Image(Long imageId, @NotNull(message = "Image_url is mandatory") String image_url) {
		this.imageId = imageId;
		this.image_url = image_url;

	}

	public Image() {
		this(0L, "");
	}

	@Override
	public String toString() {
		return "Image [imageId=" + imageId + ", image_url=" + image_url + ", product=" + product + "]";
	}

}
