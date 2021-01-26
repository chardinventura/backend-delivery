package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.entity.Authority;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationException;
import com.chardin.backenddelivery.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

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
	public Map<String, Boolean> insert(AuthorityDto authorityDto, BindingResult bindingResult) {

		if (!isValidAuthorityToInsert(authorityDto, bindingResult))
			throw new ValidationException(bindingResult);

		authorityRepository.save(dtoEntity.getAuthority(authorityDto));

		return Map.of("inserted", Boolean.TRUE);
	}

	@Override
	public AuthorityDto update(Long id, AuthorityDto authorityDto, BindingResult bindingResult) {

		Authority authority = authorityRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Authority id not found :: " + id));

		if (!isValidAuthorityToUpdate(authority, authorityDto, bindingResult))
			throw new ValidationException(bindingResult);

		authorityDto.setId(id);

		authorityRepository.save(dtoEntity.getAuthority(authorityDto));

		return authorityDto;
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

	private boolean isValidAuthorityToInsert(AuthorityDto authorityDto, BindingResult bindingResult){

		if (!bindingResult.hasFieldErrors("name") && authorityRepository.existsByName(authorityDto.getName()))
			bindingResult.rejectValue("name", "error.authority", "Name already exists");

		return !bindingResult.hasFieldErrors();
	}

	private boolean isValidAuthorityToUpdate(Authority authority, AuthorityDto authorityDto, BindingResult bindingResult){

		if (!bindingResult.hasFieldErrors("name") && !authority.getName().equals(authorityDto.getName()) && authorityRepository.existsByName(authorityDto.getName()))
			bindingResult.rejectValue("name", "error.authority", "Name already exists");

		return !bindingResult.hasFieldErrors();
	}
}