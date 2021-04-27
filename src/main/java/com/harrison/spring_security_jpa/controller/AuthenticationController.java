package com.harrison.spring_security_jpa.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
	
@RestController
public class AuthenticationController {

	@GetMapping("/login")
	public Principal login(Principal principal) {	
		return principal;
	}

}
