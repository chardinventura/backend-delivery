package com.chardin.backenddelivery.service;

import com.chardin.backenddelivery.converter.DtoEntity;
import com.chardin.backenddelivery.dto.OrderDto;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.Order;
import com.chardin.backenddelivery.exception.ResourceNotFoundException;
import com.chardin.backenddelivery.exception.ValidationException;
import com.chardin.backenddelivery.repository.OrderRepository;
import com.chardin.backenddelivery.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DtoEntity dtoEntity;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);

    @Override
    public Map<String, Boolean> insert(OrderDto orderDto, BindingResult bindingResult) {

        if (!isValidAuthorityToInsert(orderDto, bindingResult)) {
            LOGGER.error("The order isn't valid to be inserted :: " + orderDto.toString());
            throw new ValidationException(bindingResult);
        }

        orderRepository.save(dtoEntity.toEntity(orderDto));

        return Map.of("inserted", Boolean.TRUE);
    }

    @Override
    public OrderDto update(Long id, OrderDto orderDto, BindingResult bindingResult) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("The order's id couldn't be found :: " + id);
                    return new ResourceNotFoundException("The order's id couldn't be found :: " + id);
                });

        if (!isValidAuthorityToUpdate(order, orderDto, bindingResult))
        {
            LOGGER.error("The Product isn't valid to be updated :: " + id);
            throw new ValidationException(bindingResult);
        }

        orderDto.setId(id);

        orderRepository.save(dtoEntity.toEntity(orderDto));

        return orderDto;
    }

    @Override
    public Map<String, Boolean> delete(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("The order's id couldn't be found :: " + id);
                    return new ResourceNotFoundException("The order's id couldn't be found :: " + id);
                });

        orderRepository.delete(order);

        return Map.of("deleted", Boolean.TRUE);
    }

    @Override
    public List<OrderDto> getAll(Pageable pageable) {
        return dtoEntity.toOrdersDto(orderRepository
            .findAll(pageable)
            .getContent());
    }

    private boolean isValidAuthorityToInsert(OrderDto orderDto, BindingResult bindingResult) {

        UserDto userDto = orderDto.getUser();
        if (userDto != null && !userRepository.existsById(userDto.getId())) {
            LOGGER.error("The user's id couldn't be found :: " + userDto.getId());
            throw new ResourceNotFoundException("The user's id couldn't be found :: " + userDto.getId());
        }

        if (orderRepository.existsByName(orderDto.getName()))
            bindingResult.rejectValue("name", "error.order", "Name already exists");

        return !bindingResult.hasFieldErrors();
    }

    private boolean isValidAuthorityToUpdate(Order order, OrderDto orderDto, BindingResult bindingResult) {

        UserDto userDto = orderDto.getUser();
        if (userDto != null && !userRepository.existsById(userDto.getId())) {
            LOGGER.error("The user's id couldn't be found :: " + userDto.getId());
            throw new ResourceNotFoundException("The user's id couldn't be found :: " + userDto.getId());
        }

        if (!order.getName().equals(orderDto.getName()) && orderRepository.existsByName(orderDto.getName()))
            bindingResult.rejectValue("name", "error.order", "Name already exists");

        return !bindingResult.hasFieldErrors();
    }
}