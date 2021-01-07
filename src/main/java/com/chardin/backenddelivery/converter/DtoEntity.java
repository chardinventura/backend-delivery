package com.chardin.backenddelivery.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.chardin.backenddelivery.dto.RolDto;
import com.chardin.backenddelivery.dto.UserDto;
import com.chardin.backenddelivery.entity.Rol;
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
	public RolDto getRolDto(Rol rol) {

		return rol != null ? modelmapper.map(rol, RolDto.class) : null;
	}

	public Rol getRol(RolDto rolDto) {

		return rolDto != null ? modelmapper.map(rolDto, Rol.class) : null;
	}

	public List<RolDto> getRolsDto(List<Rol> rols) {

		return rols.stream().map(r -> modelmapper.map(r, RolDto.class)).collect(Collectors.toList());
	}

	public List<Rol> getRols(List<RolDto> rolsDto) {

		return rolsDto.stream().map(r -> modelmapper.map(r, Rol.class)).collect(Collectors.toList());
	}
}
