package com.chardin.backenddelivery.service;

import java.util.List;

import com.chardin.backenddelivery.dto.UserDto;

import org.springframework.data.domain.Pageable;

public interface IUserService {
	boolean insert(UserDto userDto);
	boolean update(UserDto userDto);
	boolean delete(Long id);
	UserDto getById(Long id);
	List<UserDto> getAll(Pageable pageable);
}
