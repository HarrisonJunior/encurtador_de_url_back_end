package com.harrison.spring_security_jpa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.harrison.spring_security_jpa.model.User;
import com.harrison.spring_security_jpa.service.MyUserDetailsService;

@RestController
public class UserController {
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@PostMapping("/users")
	public boolean registerUser(@RequestBody User user) {
		return myUserDetailsService.registerUser(user);
	}
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return this.myUserDetailsService.getAllUsers();
	}
	
	
}
