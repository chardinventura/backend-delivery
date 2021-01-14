package com.chardin.backenddelivery.repository;

import com.chardin.backenddelivery.entity.Authority;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

}
