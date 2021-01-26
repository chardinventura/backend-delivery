package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.OrderDto;
import com.chardin.backenddelivery.entity.Order;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationException;
import com.chardin.backenddelivery.repository.OrderRepository;
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
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private DtoEntity dtoEntity;

	private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

	@Override
	public Map<String, Boolean> insert(OrderDto orderDto, BindingResult bindingResult) {

		if (!isValidAuthorityToInsert(orderDto, bindingResult))
			throw new ValidationException(bindingResult);

		orderRepository.save(dtoEntity.getOrder(orderDto));

		return Map.of("inserted", Boolean.TRUE);
	}

	@Override
	public OrderDto update(Long id, OrderDto orderDto, BindingResult bindingResult) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order id not found :: " + id));

		if (!isValidAuthorityToUpdate(order, orderDto, bindingResult))
			throw new ValidationException(bindingResult);

		orderDto.setId(id);

		orderRepository.save(dtoEntity.getOrder(orderDto));

		return orderDto;
	}

	@Override
	public Map<String, Boolean> delete(Long id) {

		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order id not found :: " + id));

		orderRepository.deleteById(id);

		return Map.of("deleted", Boolean.TRUE);
	}

	@Override
	public List<OrderDto> getAll(Pageable pageable) {

		return orderRepository.findAll(pageable)
				.getContent()
				.stream()
				.map(o -> dtoEntity.getOrderDto(o))
				.collect(Collectors.toList());
	}

	private boolean isValidAuthorityToInsert(OrderDto orderDto, BindingResult bindingResult){

		if (!bindingResult.hasFieldErrors("name") && orderRepository.existsByName(orderDto.getName()))
			bindingResult.rejectValue("name", "error.order", "Name already exists");

		return !bindingResult.hasFieldErrors();
	}

	private boolean isValidAuthorityToUpdate(Order order, OrderDto orderDto, BindingResult bindingResult){

		if (!bindingResult.hasFieldErrors("name") && !order.getName().equals(orderDto.getName()) && orderRepository.existsByName(orderDto.getName()))
			bindingResult.rejectValue("name", "error.order", "Name already exists");

		return !bindingResult.hasFieldErrors();
	}
}