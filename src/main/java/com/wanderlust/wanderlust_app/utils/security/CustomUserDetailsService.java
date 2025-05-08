package com.wanderlust.wanderlust_app.utils.security;

import com.wanderlust.wanderlust_app.user.persistence.UserDAO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String identifier){
        return userDAO.findByEmailOrPhone(identifier)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
