package com.medicare.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medicare.model.Admin;

public interface IAdminRepository extends JpaRepository<Admin, Long> {
	Optional<Admin> findByEmailAndPassword(String email, String password);
	List<Admin> findByFirstNameContainsOrLastNameContainsOrEmailContainsOrMobileContains(String firstName, String lastName, String email, String mobile);
}
