package com.harrison.spring_security_jpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harrison.spring_security_jpa.model.Authority;
import com.harrison.spring_security_jpa.repository.AuthorityRepository;

@Service
public class AuthorityService {
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public boolean registerAuthority(Authority authority) {
		if(this.authorityRepository.save(authority) != null) {
			return true;
		}
		return false;
	}

	public List<Authority> getAllAuthorities() {
		return this.authorityRepository.findAll(); 
	}
	
	public boolean deletarAuthority(long id) {
		try {
			this.authorityRepository.deleteById(id);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
