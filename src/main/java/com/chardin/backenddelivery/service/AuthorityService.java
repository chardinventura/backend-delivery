package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.entity.Authority;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorityService implements IAuthorityService {

	@Autowired
	private AuthorityRepository rolRepository;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(AuthorityService.class);

	@Override
	public AuthorityDto insert(AuthorityDto authorityDto) {

		rolRepository.save(dtoEntity.getRol(authorityDto));

		return  authorityDto;
	}

	@Override
	public ResponseEntity update(Long id, AuthorityDto authorityDto) {

		Authority authority = rolRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Authority id not found :: " + id));

		authorityDto.setId(id);

		rolRepository.save(dtoEntity.getRol(authorityDto));

		return ResponseEntity.ok(authorityDto);
	}

	@Override
	public Map<String, Boolean> delete(Long id) {

		Authority authority = rolRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Authority id not found :: " + id));

		rolRepository.deleteById(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

	@Override
	public List<AuthorityDto> getByUser_Username(String username) {

		 return dtoEntity.getRolsDto(rolRepository.findByUsers_Username(username));
	}

	@Override
	public List<AuthorityDto> getAll(Pageable pageable) {

		return rolRepository.findAll(pageable)
				.getContent()
				.stream()
				.map(r -> dtoEntity.getRolDto(r))
				.collect(Collectors.toList());
	}
}
