package com.harrison.spring_security_jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harrison.spring_security_jpa.model.Authority;
import com.harrison.spring_security_jpa.service.AuthorityService;

@RestController
public class AuthorityController {
	
	@Autowired
	private AuthorityService authorityService;
	
	@GetMapping("/authorities")
	public List<Authority> getAllAuthorities(){
		 return this.authorityService.getAllAuthorities();
	}
	
	@PostMapping("/authorities")
	public boolean registerAuthority(@RequestBody Authority authority) {
		return this.authorityService.registerAuthority(authority);
	}
	
	@DeleteMapping("/authorities/{id}")
	public boolean deletarAuthority(@PathVariable long id) {
		return this.authorityService.deletarAuthority(id);
	}
}
