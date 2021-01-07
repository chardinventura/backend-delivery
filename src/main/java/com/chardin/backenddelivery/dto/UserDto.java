package com.chardin.backenddelivery.dto;

import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Byte avatar;
	private String name;
	private String lastname;
	private String phone;
	private String email;
	private String password;
	private List<RolDto> rols;

	public UserDto() {
	}

	public List<RolDto> getRols() {
		return rols;
	}

	public void setRols(List<RolDto> rols) {
		this.rols = rols;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Byte getAvatar() {
		return avatar;
	}
	public void setAvatar(Byte avatar) {
		this.avatar = avatar;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
