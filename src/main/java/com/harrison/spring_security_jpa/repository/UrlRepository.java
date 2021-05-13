package com.harrison.spring_security_jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.harrison.spring_security_jpa.model.Url;


@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {
	public List<Url> findAllByUserUsername(String username);
}
