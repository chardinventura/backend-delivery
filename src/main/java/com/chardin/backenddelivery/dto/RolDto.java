package com.chardin.backenddelivery.dto;

import java.io.Serializable;
import java.util.List;

public class RolDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	private List<UserDto> users;

	public RolDto() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<UserDto> getUsers() {
		return users;
	}
	public void setUsers(List<UserDto> users) {
		this.users = users;
	}
}
