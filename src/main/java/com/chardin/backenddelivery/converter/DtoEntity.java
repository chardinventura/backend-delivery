package com.chardin.backenddelivery.converter;

import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.dto.OrderDto;
import com.chardin.backenddelivery.dto.ProductDto;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.Authority;
import com.chardin.backenddelivery.entity.Order;
import com.chardin.backenddelivery.entity.Product;
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

	// to dto
	public UserDto toDto(User user) {
		return user != null ? modelmapper.map(user, UserDto.class) : null;
	}

	public AuthorityDto toDto(Authority authority) {
		return authority != null ? modelmapper.map(authority, AuthorityDto.class) : null;
	}

	public OrderDto toDto(Order order) {
		return order != null ? modelmapper.map(order, OrderDto.class) : null;
	}

	public ProductDto toDto(Product product) {
		return product != null ? modelmapper.map(product, ProductDto.class) : null;
	}

	// to Entity
	public User toEntity(UserDto userDto) {
		return userDto != null ? modelmapper.map(userDto, User.class) : null;
	}

	public Authority toEntity(AuthorityDto authorityDto) {
		return authorityDto != null ? modelmapper.map(authorityDto, Authority.class) : null;
	}

	public Order toEntity(OrderDto orderDto) {
		return orderDto != null ? modelmapper.map(orderDto, Order.class) : null;
	}

	public Product toEntity(ProductDto productDto) {
		return productDto != null ? modelmapper.map(productDto, Product.class) : null;
	}

	// to dtoList
	public List<UserDto> toUsersDto(List<User> users) {
		return users.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	public List<AuthorityDto> toAuthoritiesDto(List<Authority> authorities) {
		return authorities.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	public List<OrderDto> toOrdersDto(List<Order> orders) {
		return orders
				.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}

	public List<ProductDto> toProductsDto(List<Product> products) {
		return products
				.stream()
				.map(this::toDto)
				.collect(Collectors.toList());
	}
}