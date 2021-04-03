package com.chardin.backenddelivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@ToString(exclude = {"id"})
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	@NotBlank
	private String name;
	@DecimalMin("0.0")
	private float price;
	@JsonIgnoreProperties("products")
	private OrderDto order;
}