package com.chardin.backenddelivery.service;

import java.util.List;
import java.util.stream.Collectors;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.RolDto;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.Rol;
import com.chardin.backenddelivery.repository.RolRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RolService implements IRolService {

	@Autowired
	private RolRepository rolRepository;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(RolService.class);

	@Override
	public boolean insert(RolDto rolDto) {

		try {

			rolRepository.save(dtoEntity.getRol(rolDto));
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(RolDto rolDto) {

		try {

			rolRepository.save(dtoEntity.getRol(rolDto));
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
	public RolDto getById(Long id) {

		try {

			RolDto rolDto = dtoEntity.getRolDto(rolRepository.getOne(id));

			rolDto.getUsers().forEach(u -> u.getRols().clear());

			return rolDto;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<RolDto> getAll(Pageable pageable) {

		try {

		List<Rol> rols= rolRepository.findAll(pageable).getContent();

		List<RolDto> rolsDto = rols.stream().map(r -> dtoEntity.getRolDto(r)).collect(Collectors.toList());
		rolsDto.forEach(rd ->
				rd.getUsers().forEach(u -> u.getRols().clear()));

			return rolsDto;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
