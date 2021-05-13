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
import com.harrison.spring_security_jpa.model.User;
import com.harrison.spring_security_jpa.service.MyUserDetailsService;

@RestController
public class UserController {
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	
	@GetMapping("/users")
	public List<User> getAllUsers(){
		return this.myUserDetailsService.getAllUsers();
	}
	
	@PostMapping("/users")
	public User cadasterUser(@RequestBody User user) throws MyException {
		return this.myUserDetailsService.cadasterUser(user);
	}
	
	@PutMapping("/users/{id}")
	public User updateUser(@RequestBody User user,@PathVariable long id) throws MyException {
		return this.myUserDetailsService.updateUser(user,id);
	}

	@DeleteMapping("/users/{id}")
	public boolean deleteUser(@PathVariable long id) throws MyException {
		this.myUserDetailsService.deleteUser(id);
		return true;
	}

	
	
}
