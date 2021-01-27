package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.User;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationException;
import com.chardin.backenddelivery.repository.AuthorityRepository;
import com.chardin.backenddelivery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthorityRepository authorityRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User name not found on :: " + username));

		List<GrantedAuthority> authorities = user.getAuthorities()
				.stream()
				.map(a -> new SimpleGrantedAuthority(a.getName()))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public Map<String, Boolean> insert(UserDto userDto, BindingResult bindingResult) {

	    if (!isValidUserToInsert(userDto, bindingResult))
	        throw new ValidationException(bindingResult);

		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		userRepository.save(dtoEntity.getUser(userDto));

		return Map.of("inserted", Boolean.TRUE);
	}

	@Override
	public UserDto update(Long id, UserDto userDto, BindingResult bindingResult) {

		User user = userRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("User id not found :: " + id));

		if (!isValidUserToUpdate(user, userDto, bindingResult))
			throw new ValidationException(bindingResult);

		String passwordEncoded = bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword()) ?
				user.getPassword() : bCryptPasswordEncoder.encode(userDto.getPassword());

		userDto.setId(id);
		userDto.setPassword(passwordEncoded);
		userDto.setAuthorities(dtoEntity.getAuthoritiesDto(user.getAuthorities()));

		userRepository.save(dtoEntity.getUser(userDto));

		return userDto;
	}

	@Override
	public Map<String, Boolean> delete(Long id)  {

		User user = userRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("User id not found :: " + id));

		userRepository.delete(user);

		return Map.of("deleted", Boolean.TRUE);
	}

	@Override
	public UserDto getById(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("User id not found :: " + id));

		return dtoEntity.getUserDto(user);
	}

	@Override
	public List<UserDto> getAll(Pageable pageable) {

		return dtoEntity.getUsersDto(userRepository
				.findAll(pageable)
				.getContent());
	}

	private boolean isValidUserToInsert(UserDto userDto, BindingResult bindingResult) {

		if (!bindingResult.hasFieldErrors("username") && userRepository.existsByUsername(userDto.getUsername()))
			bindingResult.rejectValue("username", "error.user", "Username already exists");

		if (!bindingResult.hasFieldErrors("email") && userRepository.existsByEmail(userDto.getEmail()))
			bindingResult.rejectValue("email", "error.user", "Email already exists");

		if (!bindingResult.hasFieldErrors("phone") && userRepository.existsByPhone(userDto.getPhone()))
			bindingResult.rejectValue("phone", "error.user", "Phone already exists");

		return !bindingResult.hasFieldErrors();
	}

	private boolean isValidUserToUpdate(User user, UserDto userDto, BindingResult bindingResult) {

		if (!user.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername()))
			bindingResult.rejectValue("username", "error.user", "Username already exists");
		if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail()))
			bindingResult.rejectValue("email", "error.user", "Email already exists");
		if (!user.getPhone().equals(userDto.getPhone()) && userRepository.existsByPhone(userDto.getPhone()))
			bindingResult.rejectValue("phone", "error.user", "Phone already exists");

		return !bindingResult.hasFieldErrors();
	}
}