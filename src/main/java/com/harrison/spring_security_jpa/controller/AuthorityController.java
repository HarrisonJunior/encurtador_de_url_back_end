package com.harrison.spring_security_jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harrison.spring_security_jpa.exception.MyException;
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
	
	@GetMapping("/authorities/{id}")
	public Authority getAuthority(@PathVariable long id) throws MyException{
		 return this.authorityService.getAuthority(id);
	}
	
	@PostMapping("/authorities")
	public Authority cadasterAuthority(@RequestBody Authority authority) throws MyException {
		return this.authorityService.cadasterAuthority(authority);
	}
	
	@PutMapping("/authorities/{id}")
	public Authority updateAuthority(@RequestBody Authority authority,@PathVariable long id) throws MyException {
		return this.authorityService.updateAuthority(authority,id);
	}
	
	@DeleteMapping("/authorities/{id}")
	public boolean deleteAuthority(@PathVariable long id) throws MyException {
		return this.authorityService.deleteAuthority(id);
	}
}
