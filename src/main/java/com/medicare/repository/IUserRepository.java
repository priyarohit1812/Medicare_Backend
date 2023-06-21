package com.medicare.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.medicare.model.User;

public interface IUserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmailAndPassword(String email, String password);
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE mc_user u SET u.first_name = :firstName, u.last_name = :lastName, u.email = :email, u.mobile = :mobile WHERE u.user_id = :userId")
	void updateUser(String firstName, String lastName, String email, String mobile, Long userId);

}
