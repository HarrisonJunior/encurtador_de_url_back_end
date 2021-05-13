package com.harrison.spring_security_jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.harrison.spring_security_jpa.dto.AuthenticationRequestDTO;
import com.harrison.spring_security_jpa.dto.AuthenticationResponseDTO;
import com.harrison.spring_security_jpa.exception.MyException;
import com.harrison.spring_security_jpa.model.User;
import com.harrison.spring_security_jpa.util.JwtUtil;

@Service
public class AuthenticationService {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private JwtUtil jwtTokenUtil;

	public AuthenticationResponseDTO authenticate(@RequestBody AuthenticationRequestDTO authenticationRequest) throws Exception {
		final User userDetails;
		String jwt;
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword()));
			userDetails = (User) myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
			jwt = jwtTokenUtil.generateToken(userDetails);
			return new AuthenticationResponseDTO(jwt, userDetails.getId());
		}catch (BadCredentialsException e) {
			throw new MyException("Usuário ou senha inválidos. Verifique os dados informados.",HttpStatus.UNAUTHORIZED);
		}catch (DisabledException e) {
			throw new MyException("Usuário desabilitado, é necessário habilitá-lo para que o mesmo consiga se autenticar na aplicação.",HttpStatus.UNAUTHORIZED);
		}catch (LockedException e) {
			throw new MyException("Usuário bloqueado, é necessário desbloqueá-lo para que o mesmo consiga se autenticar na aplicação.",HttpStatus.UNAUTHORIZED);
		}catch (UsernameNotFoundException e) {
			throw new MyException("Usuário não encontrado. Verifique os dados informados. Por gentileza, informar um usuário válido.",HttpStatus.BAD_REQUEST);
		}
	}
}