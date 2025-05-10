package com.wanderlust.wanderlust_app.utils.auth;

import com.wanderlust.wanderlust_app.CustomRole.EntityNotFoundException;
import com.wanderlust.wanderlust_app.user.User;
import com.wanderlust.wanderlust_app.user.persistence.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class KeycloakJwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final UserDAO userDAO;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        User user = userDAO.findByKeycloakId(jwt.getSubject())
                .orElseThrow(() -> new EntityNotFoundException("No user found with given ID !"));

        return new UsernamePasswordAuthenticationToken(
                user,
                jwt,
                user.getAuthorities()
        );
    }
}