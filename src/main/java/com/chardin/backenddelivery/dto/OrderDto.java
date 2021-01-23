package com.chardin.backenddelivery.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class OrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    @NotBlank
    private String name;
    private LocalDateTime orderTime;
    @DecimalMin("0.0")
    private Float totalPrice;
    @NotBlank
    private String address;
    private List<ProductDto> productsDto;

    public OrderDto() {
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

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<ProductDto> getProductsDto() {
        return productsDto;
    }

    public void setProductsDto(List<ProductDto> productsDto) {
        this.productsDto = productsDto;
    }
}