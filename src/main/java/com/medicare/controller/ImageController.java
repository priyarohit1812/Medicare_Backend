package com.medicare.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.medicare.model.Image;
import com.medicare.model.Product;
import com.medicare.model.responses.BaseResponse;
import com.medicare.service.IImageService;
import com.medicare.service.IProductService;

@RestController()
@RequestMapping("/productimages")
public class ImageController {

	@Autowired
	private IImageService imageService;

	@Autowired
	private IProductService productService;

	@PostMapping("/upload")
	public ResponseEntity<BaseResponse<?>> uploadImage(@RequestParam("file") MultipartFile[] files,
			@RequestParam("productId") Long productId) {
		BaseResponse<?> response = new BaseResponse<>();
		String basePath = "F:\\JavaWorkspace\\Medicare\\src\\main\\resources\\static\\images\\products\\";
		String baseURL = "http://localhost:8092/images/products/";
		try {
			// Get Food Item
			Product product = this.productService.getProductById(productId);
			List<String> imageUrls = new ArrayList<>();

			for (MultipartFile file : files) {

				String fileName = product.getProductCode() + "_" + product.getProductId() + "_"
						+ file.getOriginalFilename();
				// Save file to path
				file.transferTo(new File(basePath + fileName));

				String image_Url = baseURL + fileName;
				imageUrls.add(image_Url);
				// Update URL in DB

			}
			for (String image_Url : imageUrls) {
				Image image = new Image();
				image.setImage_url(image_Url);
				image.setProduct(product);
				product.getImages().add(image);
			}

			this.productService.saveProduct(product);
			response.setIsError(false);
			response.setMessage("Image uploaded succesfully.");
			return ResponseEntity.ok(response);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			response.setIsError(true);
			response.setMessage(e.getMessage());
			return ResponseEntity.internalServerError().body(response);
		}
	}

	@DeleteMapping("/{productId}/{imageId}")
	public ResponseEntity<BaseResponse<?>> deleteImage(@PathVariable Long imageId,
			@PathVariable("productId") Long productId) {
		BaseResponse<?> response = new BaseResponse<>();
		try {
			// Get the product by ID
			Product product = productService.getProductById(productId);

			// Find the image to delete
			Optional<Image> optImage = product.getImages().stream().filter(image -> image.getImageId().equals(imageId))
					.findFirst();

			if (optImage.isPresent()) {
				Image image = optImage.get();
				// Remove the image from the product's image list
				product.getImages().remove(image);
				
				boolean isDeleted = this.imageService.deleteImage(imageId);
				productService.saveProduct(product);
				if (isDeleted) {
					response.setIsError(true);
					response.setMessage("Image deleted successfully!");
					return ResponseEntity.ok(response);
				} else {
					response.setIsError(true);
					response.setMessage("Image not found for the given product ID and image ID.");
					return ResponseEntity.internalServerError().body(response);
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			response.setIsError(true);
			response.setMessage("Failed to delete the image.");
			return ResponseEntity.internalServerError().body(response);
		}
		return ResponseEntity.ok(response);
	}
}
