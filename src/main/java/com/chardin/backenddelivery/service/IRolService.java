package com.chardin.backenddelivery.service;

import java.util.List;

import com.chardin.backenddelivery.dto.AuthorityDto;

import org.springframework.data.domain.Pageable;

public interface IRolService {
	boolean insert(AuthorityDto authorityDto);
	boolean update(AuthorityDto authorityDto);
	boolean delete(Long id);
	AuthorityDto getById(Long id);
	List<AuthorityDto> getAll(Pageable pageable);
}
