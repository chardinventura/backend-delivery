package com.chardin.backenddelivery.dto;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class AuthorityDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank
	private String name;

	public AuthorityDto() {
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
}
