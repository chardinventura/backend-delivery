package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.OrderDto;
import com.chardin.backenddelivery.dto.ProductDto;
import com.chardin.backenddelivery.entity.Product;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationException;
import com.chardin.backenddelivery.repository.OrderRepository;
import com.chardin.backenddelivery.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@Service
public class ProductService implements IProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private DtoEntity dtoEntity;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    @Override
    public Map<String, Boolean> insert(ProductDto productDto, BindingResult bindingResult) {

        if (!isValidProductToInsert(productDto, bindingResult)) {
            LOGGER.error("The Product isn't valid to be inserted :: " + productDto.toString());
            throw new ValidationException(bindingResult);
        }

        productRepository.save(dtoEntity.toEntity(productDto));

        return Map.of("inserted", Boolean.TRUE);
    }

    @Override
    public ProductDto update(Long id, ProductDto productDto, BindingResult bindingResult) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("The product's id couldn't be found :: " + id);
                    return new ResourceNotFoundException("The product's id couldn't be found :: " + id);
                });

        if (!isValidProductToUpdate(product, productDto, bindingResult)) {
            LOGGER.error("The Product isn't valid to be updated :: " + id);
            throw new ValidationException(bindingResult);
        }

        productDto.setId(id);

        productRepository.save(dtoEntity.toEntity(productDto));

        return productDto;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("The product's id couldn't be found :: " + id);
                    return new ResourceNotFoundException("The product's id couldn't be found :: " + id);
                });

        productRepository.delete(product);

        return Map.of("deleted", Boolean.TRUE);
    }

    @Override
    public List<ProductDto> getAll(Pageable pageable) {
        return dtoEntity.toProductsDto(productRepository
            .findAll(pageable)
            .getContent());
    }

    private boolean isValidProductToInsert(ProductDto productDto, BindingResult bindingResult) {

        OrderDto orderDto = productDto.getOrder();
        if (orderDto != null && !orderRepository.existsById(orderDto.getId())) {
            LOGGER.error("The order's id couldn't be found :: " + orderDto.getId());
            throw new ResourceNotFoundException("The order's id couldn't be found :: " + orderDto.getId());
        }

        if (productRepository.existsByName(productDto.getName()))
            bindingResult.rejectValue("name", "error.product", "Name already exists");

        return !bindingResult.hasFieldErrors();
    }

    private boolean isValidProductToUpdate(Product product, ProductDto productDto, BindingResult bindingResult) {

        OrderDto orderDto = productDto.getOrder();
        if (orderDto != null && !orderRepository.existsById(orderDto.getId())) {
            LOGGER.error("The order's id couldn't be found :: " + orderDto.getId());
            throw new ResourceNotFoundException("The order's id couldn't be found :: " + orderDto.getId());
        }

        if (!product.getName().equals(productDto.getName()) && productRepository.existsByName(productDto.getName()))
            bindingResult.rejectValue("name", "error.product", "Name already exists");

        return !bindingResult.hasFieldErrors();
    }
}