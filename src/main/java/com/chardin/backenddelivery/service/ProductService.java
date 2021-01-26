package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.ProductDto;
import com.chardin.backenddelivery.entity.Product;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationException;
import com.chardin.backenddelivery.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(ProductService.class);

	@Override
	public Map<String, Boolean> insert(ProductDto productDto, BindingResult bindingResult) {

		if (!isValidProductToInsert(productDto, bindingResult))
			throw new ValidationException(bindingResult);

		productRepository.save(dtoEntity.getProduct(productDto));

		return Map.of("inserted", Boolean.TRUE);
	}

	@Override
	public ProductDto update(Long id, ProductDto productDto, BindingResult bindingResult) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product id not found :: " + id));

		if (!isValidProductToUpdate(product, productDto, bindingResult))
			throw new ValidationException(bindingResult);

		productDto.setId(id);

		productRepository.save(dtoEntity.getProduct(productDto));

		return productDto;
	}

	@Override
	public Map<String, Boolean> delete(Long id) {

		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product id not found :: " + id));

		productRepository.deleteById(id);

		return Map.of("deleted", Boolean.TRUE);
	}

	@Override
	public List<ProductDto> getAll(Pageable pageable) {

		return productRepository.findAll(pageable)
				.getContent()
				.stream()
				.map(p -> dtoEntity.getProductDto(p))
				.collect(Collectors.toList());
	}

	private boolean isValidProductToInsert(ProductDto productDto, BindingResult bindingResult){

		if (!bindingResult.hasFieldErrors("name") && productRepository.existsByName(productDto.getName()))
			bindingResult.rejectValue("name", "error.product", "Name already exists");

		return !bindingResult.hasFieldErrors();
	}

	private boolean isValidProductToUpdate(Product product, ProductDto productDto, BindingResult bindingResult){

		if (!bindingResult.hasFieldErrors("name") && !product.getName().equals(productDto.getName()) && productRepository.existsByName(productDto.getName()))
			bindingResult.rejectValue("name", "error.product", "Name already exists");

		return !bindingResult.hasFieldErrors();
	}
}