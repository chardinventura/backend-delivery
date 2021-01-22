package com.chardin.backenddelivery.controller;

import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.service.IAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/authorities")
public class AuthorityController {

	@Autowired
	private IAuthorityService authorityService;

	@PostMapping
	public ResponseEntity insert(@Valid @RequestBody AuthorityDto authorityDto) {
		return authorityService.insert(authorityDto);
	}

	@PutMapping("/{id}")
	public ResponseEntity update(@PathVariable("id") Long id, @Valid @RequestBody AuthorityDto authorityDto) {
		return authorityService.update(id, authorityDto);
	}

	@DeleteMapping(path = "/{id}")
	public Map<String, Boolean> update(@PathVariable("id") Long id) {
		return authorityService.delete(id);
	}

	@GetMapping(path = "/{username}")
	public List<AuthorityDto> getById(@PathVariable("username") String username) {
		return authorityService.getByUser_Username(username);
	}

	@GetMapping
	public List<AuthorityDto> getAll(Pageable pageable) {
		return authorityService.getAll(pageable);
	}
}
