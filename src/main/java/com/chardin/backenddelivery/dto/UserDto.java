package com.chardin.backenddelivery.dto;

import com.chardin.backenddelivery.validator.Phone;
import com.chardin.backenddelivery.validator.Username;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
    @Range(max = 127)
	private byte avatar;
    @NotBlank
	private String firstname;
	@NotBlank
	private String lastname;
	@NotBlank
    @Username
	private String username;
	@NotBlank
	@Phone
	private String phone;
	@Email
	@NotBlank
	@com.chardin.backenddelivery.validator.Email
	private String email;
	@Size(min = 8)
	private String password;
	private List<AuthorityDto> authorities;

	public UserDto() {
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte getAvatar() {
		return avatar;
	}
	public void setAvatar(byte avatar) {
		this.avatar = avatar;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public List<AuthorityDto> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<AuthorityDto> authorities) {
		this.authorities = authorities;
	}
}
