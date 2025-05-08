package com.wanderlust.wanderlust_app.utils.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KeycloakJwtService {

//    @Value("${keycloak.jwks-url}")
//    private String jwksUrl;
//
//    @Value("${keycloak.issuer}")
//    private String issuer;
//
////    public Claims validateAndParseToken(String token) {
////        return Jwts.parserBuilder()
////                .setSigningKeyResolver(new Keycloak(jwksUrl))
////                .build()
////                .parseClaimsJws(token)
////                .getBody();
////    }
//
//    public String extractUsername(Claims claims) {
//        return claims.get("preferred_username", String.class);
//    }
//
//    public String extractUserType(Claims claims) {
//        return claims.get("userType", String.class);
//    }
//
//    public List<GrantedAuthority> extractAuthorities(Claims claims) {
//        return ((List<?>) claims.get("realm_access"))
//                .stream()
//                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
//                .collect(Collectors.toList());
//    }
}