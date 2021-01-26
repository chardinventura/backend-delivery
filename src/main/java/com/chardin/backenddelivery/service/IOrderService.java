package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.dto.OrderDto;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

public interface IOrderService {
	Map<String, Boolean> insert(OrderDto orderDto, BindingResult bindingResult);
	OrderDto update(Long id, OrderDto orderDto, BindingResult bindingResult);
	Map<String, Boolean> delete(Long id);
	List<OrderDto> getAll(Pageable pageable);
}
