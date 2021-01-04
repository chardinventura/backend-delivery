package com.chardin.backenddelivery.service;

import java.util.ArrayList;
import java.util.List;

import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.User;
import com.chardin.backenddelivery.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByName(username);

		return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(), new ArrayList<>());
	}

	@Override
	public boolean insert(UserDto userDto) {

		try {

			userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
			userRepository.save(new User(userDto));
			return true;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return false;
		}
	}

	@Override
	public boolean update(UserDto userDto) {

		try {

			bCryptPasswordEncoder.upgradeEncoding(userDto.getPassword());
			userRepository.save(new User(userDto));
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

			return new UserDto(userRepository.getOne(id));
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}

	@Override
	public List<UserDto> getAll(Pageable pageable) {

		List<UserDto> userDtos = new ArrayList<>();

		userRepository.findAll(pageable).getContent().forEach(u -> userDtos.add(new UserDto(u)));;

		try {

			return userDtos;
		}catch(IllegalArgumentException e) {
			LOG.error(e.getMessage());
			return null;
		}
	}
}
