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
import com.harrison.spring_security_jpa.model.Url;
import com.harrison.spring_security_jpa.service.URLService;

@RestController
public class UrlController {
	@Autowired
	private URLService urlService;

	@GetMapping("/urls/{username}")
	public List<Url> getAllUrlsByUsername(@PathVariable String username){
		return this.urlService.getAllUrlsByUsername(username);
	}

	@PostMapping("/urls")
	public Url cadasterUrl(@RequestBody Url url) throws MyException {
		return this.urlService.cadasterUrl(url);
	}

	@PutMapping("/urls/{id}")
	public Url updateUrl(@RequestBody Url url,@PathVariable long id) throws MyException {
		return this.urlService.updateUrl(url,id);
	}

	@DeleteMapping("/urls/{id}")
	public boolean deleteUrl(@PathVariable long id) throws MyException {
		this.urlService.deleteUrl(id);
		return true;
	}
}
