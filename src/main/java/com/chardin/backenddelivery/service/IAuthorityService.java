package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.dto.AuthorityDto;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface IAuthorityService {
	Map<String, Boolean> insert(AuthorityDto authorityDto, BindingResult bindingResult);
	AuthorityDto update(Long id, AuthorityDto authorityDto, BindingResult bindingResult);
	Map<String, Boolean> delete(Long id);
	List<AuthorityDto> getByUser_Username(String username);
	List<AuthorityDto> getAll(Pageable pageable);
}
