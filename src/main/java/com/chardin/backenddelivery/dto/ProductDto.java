package com.chardin.backenddelivery.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	@NotBlank
	private String name;
	@DecimalMin("0.0")
	private float price;
	private ProductDto productDto;

	public ProductDto() {
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ProductDto getProductDto() {
		return productDto;
	}
	public void setProductDto(ProductDto productDto) {
		this.productDto = productDto;
	}
}