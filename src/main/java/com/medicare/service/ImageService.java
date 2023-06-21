package com.medicare.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medicare.model.Image;
import com.medicare.repository.IImageRepository;

@Service(value = "imageService")
public class ImageService implements IImageService {
	@Autowired
	private IImageRepository imageRepository;
	
	@Override
	public List<Image> fetchImageList() {
		return this.imageRepository.findAll();
	}

	@Override
	public Image saveImage(Image image) {
		return this.imageRepository.save(image);
	}

	@Override
	public boolean deleteImage(Long imageId) {
		this.imageRepository.deleteById(imageId);
		return !this.imageRepository.existsById(imageId);
	}

	@Override
	public Image getImageById(Long imageId) {
		Optional<Image> optImage = this.imageRepository.findById(imageId);
		if (optImage != null && optImage.isPresent()) {
			return optImage.get();
		}else {
			return null;
		}
	}

}
