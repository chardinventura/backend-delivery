package com.chardin.backenddelivery.controller;

import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.dto.ProductDto;
import com.chardin.backenddelivery.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

	@Autowired
	private IProductService productService;

	@PostMapping
	public Map<String, Boolean> insert(@Valid @RequestBody ProductDto productDto, BindingResult bindingResult) {
		return productService.insert(productDto, bindingResult);
	}

	@PutMapping("/{id}")
	public ProductDto update(@PathVariable("id") Long id,
								 @Valid @RequestBody ProductDto productDto,
								 BindingResult bindingResult) {
		return productService.update(id, productDto, bindingResult);
	}

	@DeleteMapping(path = "/{id}")
	public Map<String, Boolean> delete(@PathVariable("id") Long id) {
		return productService.delete(id);
	}

	@GetMapping
	public List<ProductDto> getAll(Pageable pageable) {
		return productService.getAll(pageable);
	}
}
