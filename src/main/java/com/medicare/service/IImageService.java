package com.medicare.service;

import java.util.List;

import com.medicare.model.Image;


public interface IImageService {
	public List<Image> fetchImageList();
	public Image saveImage(Image image);
	boolean deleteImage(Long imageId);
	public Image getImageById(Long imageId);
	
}
