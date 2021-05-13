package com.harrison.spring_security_jpa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harrison.spring_security_jpa.dto.AuthenticationRequestDTO;
import com.harrison.spring_security_jpa.service.AuthenticationService;

@RestController
public class AuthenticationController {
	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/login")
	public  ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception{
		return ResponseEntity.ok(this.authenticationService.authenticate(authenticationRequest));
	}
}
