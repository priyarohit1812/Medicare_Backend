package com.medicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.model.Image;

public interface IImageRepository extends JpaRepository<Image, Long> {

}
