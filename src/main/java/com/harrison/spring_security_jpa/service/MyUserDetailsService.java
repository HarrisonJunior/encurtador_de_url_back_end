package com.harrison.spring_security_jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harrison.spring_security_jpa.exception.MyException;
import com.harrison.spring_security_jpa.model.Authority;
import com.harrison.spring_security_jpa.model.User;
import com.harrison.spring_security_jpa.repository.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = this.userRepository.findUserByUsername(username);
		// Lançando exception caso o user seja vazio, porque nao existe
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
		// Adicionando o ROLE_ antes de cada regra do usuário
		user.get().getAuthorities().forEach(n -> {
			((Authority) n).setAuthority("ROLE_" + n.getAuthority());
		});
		return user.get();
	}
	
	public List<User> getAllUsers(){
		return this.userRepository.findAll();
	}

	public User cadasterUser(User user) throws MyException {
		if (this.userRepository.findUserByUsername(user.getUsername()).isEmpty()) {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			this.userRepository.save(user);
			return user;
		}else{
			throw new MyException("Não foi possível cadastrar o usuário, usuário já existe",HttpStatus.BAD_REQUEST);
		}	
	}
	
	public User updateUser(User user, long id) throws MyException {
		Optional<User> userReturned = this.userRepository.findById(id);
		userReturned.orElseThrow(()-> new MyException("Não foi possível atualizar o recurso, uma vez que ele não existe",HttpStatus.NOT_FOUND));
		user.setId(id);
		this.userRepository.save(user);
		return user;
	}

	public boolean deleteUser(long id) throws MyException {
		try {
			this.userRepository.deleteById(id);
			return true;
		}catch (EmptyResultDataAccessException e) {
			throw new MyException("Ocorreu um erro. Recurso informada não existe, logo não é possível deletá-lo", HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			throw new MyException("Ocorreu um erro. Não foi possível deletar", HttpStatus.BAD_REQUEST);
		}
	}

}
