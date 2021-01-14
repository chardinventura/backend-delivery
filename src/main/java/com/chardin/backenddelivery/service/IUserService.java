package com.chardin.backenddelivery.service;

import java.util.List;
import java.util.Map;

import com.chardin.backenddelivery.dto.UserDto;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
	UserDto insert(UserDto userDto);
	ResponseEntity update(Long userId, UserDto userDto);
	Map<String, Boolean> delete(Long id);
	ResponseEntity getById(Long id);
	List<UserDto> getAll(Pageable pageable);
}
