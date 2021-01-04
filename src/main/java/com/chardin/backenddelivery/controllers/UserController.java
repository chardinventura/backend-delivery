package com.chardin.backenddelivery.controllers;

import java.util.List;

import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping
	public boolean insert(@RequestBody UserDto userDto) {
		return userService.insert(userDto);
	}

	@PutMapping
	public boolean update(@RequestBody UserDto userDto) {
		return userService.update(userDto);
	}

	@DeleteMapping(path = "/{id}")
	public boolean update(@PathVariable("id") Long id) {
		return userService.delete(id);
	}

	@GetMapping(path = "/{id}")
	public UserDto getById(@PathVariable("id") Long id) {
		return userService.getById(id);
	}

	@GetMapping
	public List<UserDto> getAll(Pageable pageable) {
		return userService.getAll(pageable);
	}
}
