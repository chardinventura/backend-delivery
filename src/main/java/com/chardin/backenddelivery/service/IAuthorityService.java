package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.dto.UserDto;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IAuthorityService {
	ResponseEntity insert(AuthorityDto authorityDto);
	ResponseEntity update(Long id, AuthorityDto authorityDto);
	Map<String, Boolean> delete(Long id);
	List<AuthorityDto> getByUser_Username(String username);
	List<AuthorityDto> getAll(Pageable pageable);
}
