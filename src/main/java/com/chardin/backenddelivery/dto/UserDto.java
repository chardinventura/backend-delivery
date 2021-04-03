package com.chardin.backenddelivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@ToString(exclude = {"id", "password"})
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
	private String username;
	@NotBlank
	private String phone;
	@Size(min = 8)
	@NotBlank
	private String password;
	@JsonIgnoreProperties("users")
	private List<AuthorityDto> authorities;

	@JsonIgnore
	public String getPassword() {
		return password;
	}
	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}
}