package com.harrison.spring_security_jpa.dto;

import java.io.Serializable;

public class AuthenticationResponseDTO implements Serializable {

	private String jwt;
	private long userId;
	
	public AuthenticationResponseDTO() {
	}

	public AuthenticationResponseDTO(String jwt, long userId) {
		super();
		this.jwt = jwt;
		this.userId = userId;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
}
