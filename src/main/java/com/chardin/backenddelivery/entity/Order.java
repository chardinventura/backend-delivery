package com.chardin.backenddelivery.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private LocalDateTime orderTime;
    private Float totalPrice;
    private String address;
    @OneToMany(mappedBy = "order")
    private List<Product> products;
    @ManyToOne
    private User user;
}