package com.harrison.spring_security_jpa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

		// Adicionando o ROLE_ antes de cada regra do usuário
		user.get().getAuthorities().forEach(n -> {
			((Authority) n).setAuthority("ROLE_" + n.getAuthority());
		});

		// Lançando erro caso o user seja vazio
		user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));

		return user.get();
	}

	public boolean registerUser(User user) {
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		if (userRepository.save((User) user) != null) {
			return true;
		} else {
			return false;
		}
	}

	public List<User> getAllUsers() {
		return this.userRepository.findAll();
	}

}
