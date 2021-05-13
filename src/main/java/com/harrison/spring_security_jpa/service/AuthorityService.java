package com.harrison.spring_security_jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.harrison.spring_security_jpa.exception.MyException;
import com.harrison.spring_security_jpa.model.Authority;
import com.harrison.spring_security_jpa.repository.AuthorityRepository;

@Service
public class AuthorityService {
	@Autowired
	private AuthorityRepository authorityRepository;
	
	public List<Authority> getAllAuthorities(){
		return this.authorityRepository.findAll();
	}
	
	public Authority getAuthority(long id) throws MyException{
		Optional<Authority> authority = this.authorityRepository.findById(id);
		authority.orElseThrow(() -> new MyException("Recurso não encontrado", HttpStatus.NOT_FOUND));
		return authority.get();
	}
	
	public Authority cadasterAuthority(Authority authority) throws MyException {
		if(this.authorityRepository.save(authority) != null) {
			return authority;
		}
		throw new MyException("Não foi possível cadastrar o recurso",HttpStatus.BAD_REQUEST);
	}
	
	public Authority updateAuthority(Authority authority, long id) throws MyException {
		Optional<Authority> authorityReturned = this.authorityRepository.findById(id);
		authorityReturned.orElseThrow(()-> new MyException("Não foi possível atualizar o recurso, uma vez que ele não existe",HttpStatus.NOT_FOUND));
		authority.setId(id);
		this.authorityRepository.save(authority);
		return authority;
	}
	
	public boolean deleteAuthority(long id) throws MyException {
		try {
			this.authorityRepository.deleteById(id);
			return true;
		}catch (EmptyResultDataAccessException e) {
			throw new MyException("Ocorreu um erro. Recurso informado não existe, logo não é possível deletá-lo", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			throw new MyException("Ocorreu um erro. Não foi possível deletar", HttpStatus.BAD_REQUEST);
		}
	}

}
