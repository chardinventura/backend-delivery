package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.dto.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface IProductService {
	Map<String, Boolean> insert(ProductDto productDto, BindingResult bindingResult);
	ProductDto update(Long id, ProductDto productDto, BindingResult bindingResult);
	Map<String, Boolean> delete(Long id);
	List<ProductDto> getAll(Pageable pageable);
}
