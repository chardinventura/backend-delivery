package com.chardin.backenddelivery.service;

import java.util.List;

import com.chardin.backenddelivery.dto.RolDto;

import org.springframework.data.domain.Pageable;

public interface IRolService {
	boolean insert(RolDto rolDto);
	boolean update(RolDto rolDto);
	boolean delete(Long id);
	RolDto getById(Long id);
	List<RolDto> getAll(Pageable pageable);
}
