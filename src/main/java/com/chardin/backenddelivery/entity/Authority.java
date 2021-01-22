package com.chardin.backenddelivery.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authorities")
public class Authority implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, unique = true)
	private String name;
	@ManyToMany
	@JoinTable(name = "users_authorities",
			joinColumns = @JoinColumn(name = "authority_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> users;

	public Authority() {
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}
}
