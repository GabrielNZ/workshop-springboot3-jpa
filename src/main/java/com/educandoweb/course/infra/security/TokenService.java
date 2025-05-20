package com.educandoweb.course.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.educandoweb.course.entities.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    String secret;

    public String generateToken(User user) {
       try {
           Algorithm algorithm = Algorithm.HMAC256(secret);
           String token = JWT.create().withIssuer("JWT_Token_API").withSubject(user.getName()).withExpiresAt(generateExpirationTime()).sign(algorithm);
           return token;
       }catch (JWTCreationException exception){
           throw new RuntimeException(exception);
       }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String tokenjwt = JWT.require(algorithm).withIssuer("JWT_Token_API").build().verify(token).getSubject();
            return tokenjwt;
        } catch (JWTVerificationException e) {
            return "";
        }
    }

    public Instant generateExpirationTime() {
        return Instant.now().plusSeconds(3600);
    }
}
