package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.repository.AuthorityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorityService implements IRolService {

	@Autowired
	private AuthorityRepository rolRepository;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(AuthorityService.class);

	@Override
	public boolean insert(AuthorityDto authorityDto) {

		try {
			rolRepository.save(dtoEntity.getRol(authorityDto));
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(AuthorityDto authorityDto) {

		try {

			rolRepository.save(dtoEntity.getRol(authorityDto));
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(Long id) {

		try {

			rolRepository.deleteById(id);
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public AuthorityDto getById(Long id) {

		AuthorityDto authorityDto = dtoEntity.getRolDto(rolRepository.findById(id).get());

		try {

			return authorityDto;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return authorityDto;
		}
	}

	@Override
	public List<AuthorityDto> getAll(Pageable pageable) {

		try {
			List<AuthorityDto> authorities = rolRepository.findAll(pageable).getContent()
					.stream().map(r -> dtoEntity.getRolDto(r)).collect(Collectors.toList());

			return authorities;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
