package com.chardin.backenddelivery.dto;

import java.io.Serializable;
import java.util.List;

import com.chardin.backenddelivery.entity.User;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private byte avatar;
	private String name;
	private String lastname;
	private String email;
	private String password;
	private String phone;
	private String address;
	private List<RolDto> rols;

	public UserDto() {
	}

	public UserDto(User user) {
		this.id = user.getId();
		this.avatar = user.getAvatar();
		this.name = user.getName();
		this.lastname = user.getLastname();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.phone = user.getPhone();
		this.address = user.getAddress();
		user.getRols().forEach(r -> rols.add(new RolDto(r)));
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<RolDto> getRols() {
		return rols;
	}

	public void setRols(List<RolDto> rols) {
		this.rols = rols;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public byte getAvatar() {
		return avatar;
	}
	public void setAvatar(byte avatar) {
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
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
