package com.wanderlust.wanderlust_app.auth;

import com.wanderlust.wanderlust_app.auth.DTOs.AuthResponseDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.LoginDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.RegisterDTO;
import com.wanderlust.wanderlust_app.auth.interfaces.AuthService;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImp implements AuthService {
    @Override
    public AuthResponseDTO login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public AuthResponseDTO register(RegisterDTO registerDTO) {
        return null;
    }
}
