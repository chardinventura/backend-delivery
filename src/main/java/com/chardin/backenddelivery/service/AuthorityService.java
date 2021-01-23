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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorityService implements IAuthorityService {

	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(AuthorityService.class);

	@Override
	public ResponseEntity insert(AuthorityDto authorityDto) {

		if (authorityRepository.existsByName(authorityDto.getName()))
			return responseBadRequest();

		authorityRepository.save(dtoEntity.getAuthority(authorityDto));

		return ResponseEntity.ok(authorityDto);
	}

	@Override
	public ResponseEntity update(Long id, AuthorityDto authorityDto) {

		Authority authority = authorityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Authority id not found :: " + id));

		if (!authority.getName().equals(authorityDto.getName()) && authorityRepository.existsByName(authorityDto.getName()))
			return responseBadRequest();

		authorityDto.setId(id);

		authorityRepository.save(dtoEntity.getAuthority(authorityDto));

		return ResponseEntity.ok(authorityDto);
	}

	@Override
	public Map<String, Boolean> delete(Long id) {

		Authority authority = authorityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Authority id not found :: " + id));

		authorityRepository.deleteById(id);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

	@Override
	public List<AuthorityDto> getByUser_Username(String username) {

		 return dtoEntity.getAuthoritiesDto(authorityRepository.findByUsers_Username(username));
	}

	@Override
	public List<AuthorityDto> getAll(Pageable pageable) {

		return authorityRepository.findAll(pageable)
				.getContent()
				.stream()
				.map(r -> dtoEntity.getAuthorityDto(r))
				.collect(Collectors.toList());
	}

	private ResponseEntity responseBadRequest(){

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		Map<String, Object> body = new HashMap<>();
		body.put("message", httpStatus.getReasonPhrase());
		body.put("status", httpStatus.value());
		body.put("timeStamp", LocalDateTime.now());

		body.put("error", "Name already exists");

		return new ResponseEntity(body, httpStatus);
	}
}
