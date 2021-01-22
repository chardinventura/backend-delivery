package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.User;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationResponse;
import com.chardin.backenddelivery.repository.AuthorityRepository;
import com.chardin.backenddelivery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
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

		List<GrantedAuthority> authorities = authorityRepository.findByUsers_Username(username)
				.stream()
				.map(a -> new SimpleGrantedAuthority(a.getName()))
				.collect(Collectors.toList());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}

	@Override
	public ResponseEntity insert(UserDto userDto) {

	    if (userRepository.existsByUsernameOrEmailOrPhone(userDto.getUsername(), userDto.getEmail(), userDto.getPhone()))
	    	return responseBadPost(userDto);

		userDto.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

		userRepository.save(dtoEntity.getUser(userDto));

		return ResponseEntity.ok(userDto);
	}

	@Override
	public ResponseEntity update(Long id, UserDto userDto) {

		User user = userRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("User id not found :: " + id));

		if (!user.getUsername().equals(userDto.getUsername()) || !user.getEmail().equals(userDto.getEmail()) || !user.getPhone().equals(userDto.getPhone()))
			return responseBadPut(user, userDto);

		String passwordEncoded = bCryptPasswordEncoder.matches(userDto.getPassword(), user.getPassword()) ?
				user.getPassword() : bCryptPasswordEncoder.encode(userDto.getPassword());

		userDto.setId(id);
		userDto.setPassword(passwordEncoded);

		userRepository.save(dtoEntity.getUser(userDto));

		return ResponseEntity.ok(userDto);
	}

	@Override
	public Map<String, Boolean> delete(Long id)  {

		User user = userRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("User id not found :: " + id));

		userRepository.delete(user);

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

	@Override
	public ResponseEntity getById(Long id) {

		User user = userRepository.findById(id)
				.orElseThrow(() ->  new ResourceNotFoundException("User id not found :: " + id));

		return ResponseEntity.ok(dtoEntity.getUserDto(user));
	}

	@Override
	public List<UserDto> getAll(Pageable pageable) {

		return  userRepository.findAll(pageable)
				.getContent()
				.stream()
				.map(u -> dtoEntity.getUserDto(u))
				.collect(Collectors.toList());
	}

	private ResponseEntity responseBadPost(UserDto userDto){

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		ValidationResponse body = new ValidationResponse();
		body.setMessage(httpStatus.getReasonPhrase());
		body.setStatus(httpStatus.value());
		body.setTimeStamp(LocalDateTime.now());

		Map<String, String> errors =  new HashMap<>();

		if (userRepository.existsByUsername(userDto.getUsername()))
			errors.put("username", "Username already exists");
		if (userRepository.existsByEmail(userDto.getEmail()))
			errors.put("email", "Email already exists");
		if (userRepository.existsByPhone(userDto.getPhone()))
			errors.put("phone", "Phone already exists");

		body.setErrors(errors);

		return new ResponseEntity(body, httpStatus);
	}

	private ResponseEntity responseBadPut(User user, UserDto userDto) {

		HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

		ValidationResponse body = new ValidationResponse();
		body.setMessage(httpStatus.getReasonPhrase());
		body.setStatus(httpStatus.value());
		body.setTimeStamp(LocalDateTime.now());

		Map<String, String> errors =  new HashMap<>();

		if (!user.getUsername().equals(userDto.getUsername()) && userRepository.existsByUsername(userDto.getUsername()))
			errors.put("username", "Username already exists");
		if (!user.getEmail().equals(userDto.getEmail()) && userRepository.existsByEmail(userDto.getEmail()))
			errors.put("email", "Email already exists");
		if (!user.getPhone().equals(userDto.getPhone()) && userRepository.existsByPhone(userDto.getPhone()))
			errors.put("phone", "Phone already exists");

		body.setErrors(errors);

		return new ResponseEntity(body, httpStatus);
	}
}