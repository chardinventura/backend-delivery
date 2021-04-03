package com.chardin.backenddelivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

@Data
@ToString(exclude = {"id"})
public class AuthorityDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	@NotBlank
	private String name;
	@JsonIgnoreProperties("authorities")
	private List<UserDto> users;
}
