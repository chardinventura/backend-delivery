package com.chardin.backenddelivery.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString(exclude = {"id"})
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
    @JsonIgnoreProperties("order")
    private List<ProductDto> products;
    @JsonIgnoreProperties("orders")
    private UserDto user;
}