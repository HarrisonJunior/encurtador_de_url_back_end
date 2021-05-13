package com.harrison.spring_security_jpa.util;

import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtUtil {
	
	private String key = "secret";  //pode ser um hash ou uma string maior
	private static final long expirationTime = 31536000000L; 
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setSubject(userDetails.getUsername())   //informa quem está criando o token, normalmente usa-se o id do usuário ou username
				.setExpiration(new Date(System.currentTimeMillis() + this.expirationTime ))
				.signWith(SignatureAlgorithm.HS256, this.key)
				.compact();
	}
	
	public Claims decodeToken(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(this.key)
					.parseClaimsJws(token)
					.getBody();
		}catch (SignatureException e) {
			throw new SignatureException("O token é inválido!");
		}catch (MalformedJwtException e) {
			throw new MalformedJwtException("O token é inválido!");
		}
	}
	
	public boolean validateToken(String token, UserDetails userDetails) {
		String tokenTratado = token.replace("Bearer ", "");
		Claims claims = decodeToken(tokenTratado);		
		/*verifica se o token expirou e se o username do token é igual ao usuario do security context*/
		if(!this.isTokenExpired(token) && this.extractUsername(token).equals(userDetails.getUsername())) {
			return true;
		}else if(this.isTokenExpired(token)) {
			System.out.println("O token expirou!");
		}
		return false;
	}
	
    public Boolean isTokenExpired(String token) {
    	Claims claims = decodeToken(token);
        return claims.getExpiration().before(new Date(System.currentTimeMillis()));
    }
    
    public String extractUsername(String token) {
    	Claims claims = decodeToken(token);
    	return claims.getSubject();
    }
    
    public Date extractExpiration(String token) {
    	Claims claims = decodeToken(token);
    	return claims.getExpiration();
    }
	
}
