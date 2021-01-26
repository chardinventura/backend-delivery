package com.chardin.backenddelivery.controller;

import com.chardin.backenddelivery.dto.OrderDto;
import com.chardin.backenddelivery.dto.ProductDto;
import com.chardin.backenddelivery.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/orders")
public class OrderController {

	@Autowired
	private IOrderService orderService;

	@PostMapping
	public Map<String, Boolean> insert(@Valid @RequestBody OrderDto orderDto, BindingResult bindingResult) {
		return orderService.insert(orderDto, bindingResult);
	}

	@PutMapping("/{id}")
	public OrderDto update(@PathVariable("id") Long id,
								 @Valid @RequestBody OrderDto orderDto,
								 BindingResult bindingResult) {
		return orderService.update(id, orderDto, bindingResult);
	}

	@DeleteMapping(path = "/{id}")
	public Map<String, Boolean> delete(@PathVariable("id") Long id) {
		return orderService.delete(id);
	}

	@GetMapping
	public List<OrderDto> getAll(Pageable pageable) {
		return orderService.getAll(pageable);
	}
}
