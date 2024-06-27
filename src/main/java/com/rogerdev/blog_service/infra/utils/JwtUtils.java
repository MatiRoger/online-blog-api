package com.rogerdev.blog_service.infra.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    @Value("${security.jwt.private.key}")
    private String privateKey;
    @Value("${security.jwt.user.generator}")
    private String userGenerator;

    public String generateToken (Authentication auth) {

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String username = auth.getPrincipal().toString();
        String authorities = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return JWT.create()
                .withIssuer(this.userGenerator)
                .withSubject(username)
                .withClaim("authorities", authorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date())
                .sign(algorithm);

    }
    public DecodedJWT validateToken (String token) {
        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);
        try {
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(this.userGenerator).build();

            return verifier.verify(extractTokenPrefix(token));

        } catch (JWTVerificationException e) {
            throw new JWTVerificationException(e.toString());
        }
    }
    public String extractUsername (DecodedJWT decodedJWT) {
        return decodedJWT.getSubject();
    }
    public Claim extractSpecificClaim(DecodedJWT decodedJWT, String claim) {
        return decodedJWT.getClaim(claim);
    }
    public Map<String, Claim> getClaims (DecodedJWT decodedJWT) {
        return decodedJWT.getClaims();
    }

    public String extractTokenPrefix (String token) {
        return token.startsWith("Bearer ") ? token.substring(7) : token;
    }
}
