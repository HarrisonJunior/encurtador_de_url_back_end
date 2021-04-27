package com.harrison.spring_security_jpa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harrison.spring_security_jpa.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findUserByUsername(String username);
}
