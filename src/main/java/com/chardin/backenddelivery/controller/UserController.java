package com.chardin.backenddelivery.controller;

import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private IUserService userService;

	@PostMapping
	public ResponseEntity insert(@Valid @RequestBody UserDto userDto) {
		return userService.insert(userDto);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity update(@Valid @PathVariable("id") Long id, @RequestBody UserDto userDto) {
		return userService.update(id, userDto);
	}

	@DeleteMapping(path = "/{id}")
	public Map<String, Boolean> delete(@PathVariable("id") Long id) {
		return userService.delete(id);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity getById(@PathVariable("id") Long id) {
		return userService.getById(id);
	}

	@GetMapping
	public List<UserDto> getAll(Pageable pageable) {
		return userService.getAll(pageable);
	}
}