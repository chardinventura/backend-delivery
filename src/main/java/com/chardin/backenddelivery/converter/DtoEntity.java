package com.chardin.backenddelivery.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.chardin.backenddelivery.dto.AuthorityDto;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.Authority;
import com.chardin.backenddelivery.entity.User;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
	public AuthorityDto getRolDto(Authority authorities) {

		return authorities != null ? modelmapper.map(authorities, AuthorityDto.class) : null;
	}

	public Authority getRol(AuthorityDto authorityDto) {

		return authorityDto != null ? modelmapper.map(authorityDto, Authority.class) : null;
	}

	public List<AuthorityDto> getRolsDto(List<Authority> authorities) {

		return authorities.stream().map(r -> modelmapper.map(r, AuthorityDto.class)).collect(Collectors.toList());
	}

	public List<Authority> getRols(List<AuthorityDto> rolsDto) {

		return rolsDto.stream().map(r -> modelmapper.map(r, Authority.class)).collect(Collectors.toList());
	}
}
