package com.chardin.backenddelivery.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.User;
import com.chardin.backenddelivery.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(email);

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public boolean insert(UserDto userDto) {

		try {
			userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			userRepository.save(dtoEntity.getUser(userDto));
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(UserDto userDto) {

		try {

			if(!userRepository.findById(userDto.getId()).get().getPassword().equals(userDto.getPassword()))
				userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

			userRepository.save(dtoEntity.getUser(userDto));
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean delete(Long id) {

		try {

			userRepository.deleteById(id);
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public UserDto getById(Long id) {

		try {
			UserDto userDto = dtoEntity.getUserDto(userRepository.getOne(id));

			userDto.getRols().forEach(r -> r.getUsers().clear());

			return userDto;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<UserDto> getAll(Pageable pageable) {

		try {

		List<User> users = userRepository.findAll(pageable).getContent();

		List<UserDto> usersDto = users.stream().map(u -> dtoEntity.getUserDto(u)).collect(Collectors.toList());
		usersDto.forEach(ud ->
				ud.getRols().forEach(r -> r.getUsers().clear()));

			return usersDto;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
