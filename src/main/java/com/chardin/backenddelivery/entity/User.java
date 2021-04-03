package com.chardin.backenddelivery.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	private byte avatar;
	@Column(nullable = false)
	private String firstname;
	@Column(nullable = false)
	private String lastname;
	@Column(nullable = false, unique = true)
	private String username;
	@Column(nullable = false, unique = true)
	private String phone;
	@Column(nullable = false)
	private String password;
	@ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
	private List<Authority> authorities;
	@OneToMany(mappedBy = "user")
	private List<Order> orders;
}