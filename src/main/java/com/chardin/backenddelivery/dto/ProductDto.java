package com.chardin.backenddelivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
	@JsonIgnoreProperties("products")
	private OrderDto order;

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
	public OrderDto getOrder() {
		return order;
	}
	public void setOrder(OrderDto order) {
		this.order = order;
	}
}