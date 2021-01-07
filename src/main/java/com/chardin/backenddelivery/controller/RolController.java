package com.chardin.backenddelivery.controller;

import java.util.List;

import com.chardin.backenddelivery.dto.RolDto;
import com.chardin.backenddelivery.dto.RolDto;
import com.chardin.backenddelivery.service.IRolService;

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
@RequestMapping("/v1/rols")
public class RolController {

	@Autowired
	private IRolService rolService;

	@PostMapping
	public boolean insert(@RequestBody RolDto rolDto) {
		return rolService.insert(rolDto);
	}

	@PutMapping
	public boolean update(@RequestBody RolDto rolDto) {
		return rolService.update(rolDto);
	}

	@DeleteMapping(path = "/{id}")
	public boolean update(@PathVariable("id") Long id) {
		return rolService.delete(id);
	}

	@GetMapping(path = "/{id}")
	public RolDto getById(@PathVariable("id") Long id) {
		return rolService.getById(id);
	}

	@GetMapping
	public List<RolDto> getAll(Pageable pageable) {
		return rolService.getAll(pageable);
	}
}
