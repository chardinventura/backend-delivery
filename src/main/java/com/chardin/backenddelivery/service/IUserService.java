package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface IUserService extends UserDetailsService {
	Map<String, Boolean> insert(UserDto userDto, BindingResult bindingResult);
	UserDto update(Long userId, UserDto userDto, BindingResult bindingResult);
	Map<String, Boolean> delete(Long id);
	UserDto getById(Long id);
	List<UserDto> getAll(Pageable pageable);
}
