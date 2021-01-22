package com.chardin.backenddelivery.repository;

import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Boolean existsByUsernameOrEmailOrPhone(String username, String email, String phone);
	Boolean existsByUsername(String username);
	Boolean existsByPhone(String phone);
	Boolean existsByEmail(String email);
}
