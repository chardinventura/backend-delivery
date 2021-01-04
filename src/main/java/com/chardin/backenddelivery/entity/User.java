package com.chardin.backenddelivery.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.chardin.backenddelivery.dto.UserDto;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false)
	private byte avatar;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false, unique = true)
	private String phone;
	@Column(nullable = false)
	private String address;
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Rol> rols;

	public User() {
	}
	public User(UserDto userDto) {
		this.id = userDto.getId();
		this.avatar = userDto.getAvatar();
		this.name = userDto.getName();
		this.lastname = userDto.getLastname();
		this.email = userDto.getEmail();
		this.password = userDto.getPassword();
		this.phone = userDto.getPhone();
		this.address = userDto.getAddress();
		userDto.getRols().forEach(r -> rols.add(new Rol(r)));
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public List<Rol> getRols() {
		return rols;
	}

	public void setRols(List<Rol> rols) {
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
