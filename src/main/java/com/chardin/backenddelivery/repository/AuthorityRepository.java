package com.chardin.backenddelivery.repository;

import com.chardin.backenddelivery.entity.Authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
    List<Authority> findByUsers_Username(String username);
}
