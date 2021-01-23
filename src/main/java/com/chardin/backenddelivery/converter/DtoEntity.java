package com.chardin.backenddelivery.converter;

import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.dto.OrderDto;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.Authority;
import com.chardin.backenddelivery.entity.Order;
import com.chardin.backenddelivery.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoEntity {

	@Autowired
	private ModelMapper modelmapper;

	//User-UserDto
	public UserDto getUserDto(User user) {

		return user != null ? modelmapper.map(user, UserDto.class) : null;
	}

	public User getUser(UserDto userDto) {

		return userDto != null ? modelmapper.map(userDto, User.class) : null;
	}

	public List<UserDto> getUsersDto(List<User> users) {

		return users.stream().map(u -> modelmapper.map(u, UserDto.class)).collect(Collectors.toList());
	}

	public List<User> getUsers(List<UserDto> usersDto) {

		return usersDto.stream().map(u -> modelmapper.map(u, User.class)).collect(Collectors.toList());
	}

	//Rol-RolDto
	public AuthorityDto getAuthorityDto(Authority authority) {

		return authority != null ? modelmapper.map(authority, AuthorityDto.class) : null;
	}

	public Authority getAuthority(AuthorityDto authorityDto) {

		return authorityDto != null ? modelmapper.map(authorityDto, Authority.class) : null;
	}

	public List<AuthorityDto> getAuthoritiesDto(List<Authority> authorities) {

		return authorities
				.stream()
				.map(r -> modelmapper.map(r, AuthorityDto.class))
				.collect(Collectors.toList());
	}

	public List<Authority> getAuthorities(List<AuthorityDto> authoritiesDto) {

		return authoritiesDto
				.stream()
				.map(r -> modelmapper.map(r, Authority.class))
				.collect(Collectors.toList());
	}
	
	//Order
	public OrderDto getOrderDto(Order order) {

		return order != null ? modelmapper.map(order, OrderDto.class) : null;
	}

	public Order getOrder(OrderDto orderDto) {

		return orderDto != null ? modelmapper.map(orderDto, Order.class) : null;
	}

	public List<OrderDto> getOrdersDto(List<Order> orders) {

		return orders
				.stream()
				.map(r -> modelmapper.map(r, OrderDto.class))
				.collect(Collectors.toList());
	}

	public List<Order> getOrders(List<OrderDto> ordersDto) {

		return ordersDto
				.stream()
				.map(r -> modelmapper.map(r, Order.class))
				.collect(Collectors.toList());
	}
}