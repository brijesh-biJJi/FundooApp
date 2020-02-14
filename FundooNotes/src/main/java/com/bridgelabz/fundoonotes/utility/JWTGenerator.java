package com.bridgelabz.fundoonotes.utility;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Component
public class JWTGenerator {
private static final String SECRET = "thedoctor46";
	
	public String encryptToken(long l) {
		String token = null;
		
		token = JWT.create().withClaim("id", l).sign(Algorithm.HMAC512(SECRET));
		System.out.println("Generated Token :"+token);
		return token;
	}
	
	public long decryptToken(String jwtToken)
	{
		Long userId = (long) 0;
		if (jwtToken != null) {
			userId = JWT.require(Algorithm.HMAC512(SECRET)).build().verify(jwtToken).getClaim("id").asLong();
		}
		return userId;
		
	}
}
