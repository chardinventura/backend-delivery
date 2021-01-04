package com.chardin.backenddelivery.dto;

import java.io.Serializable;

import com.chardin.backenddelivery.entity.Rol;

public class RolDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	private UserDto user;

	public RolDto() {
	}
	public RolDto(Rol rol) {
		this.id = rol.getId();
		this.name = rol.getName();
		this.user = new UserDto(rol.getUser());
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
