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

import com.harrison.spring_security_jpa.model.Url;
import com.harrison.spring_security_jpa.service.URLService;

@RestController
public class UrlController {
	@Autowired
	private URLService urlService;

	/**
	 * Método responsável por retornar todas as urls de um usuário
	 * 
	 * @param username
	 * @return lista de url
	 */
	@GetMapping("/urls/{username}")
	public List<Url> getAllUrlsByUsername(@PathVariable String username) {
		return this.urlService.getAllUrlsByUsername(username);
	}

	/**
	 * Método cadastra uma url
	 * 
	 * @param url
	 * @return url cadastrada
	 */
	@PostMapping("/urls")
	public Url registerUrl(@RequestBody Url url) {
//		System.out.println(url.getId()  + " " + url.getUser().getId() + url.getUser().getUsername());
		return this.urlService.registerUrl(url);
	}

	/**
	 * Método atualiza uma url pré cadastrada
	 * 
	 * @param url
	 */
	@PutMapping("/urls")
	public boolean updateUrl(@RequestBody Url url) {
		return this.urlService.updateUrl(url);
	}

	/**
	 * Método deleta uma url pré cadastrada através do id fornecido
	 * 
	 * @param id
	 */
	@DeleteMapping("/urls/{id}")
	public boolean deleteUrl(@PathVariable long id) {
		this.urlService.deleteUrl(id);
		return true;
	}
}
