package com.medicare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.medicare.model.Address;

public interface IAddressRepository extends JpaRepository<Address, Long> {
	@Query(nativeQuery = true, value = "SELECT * FROM mc_address WHERE user_id_fk = :userId")
	List<Address> findByUserId(@Param("userId") Long userId);
}
