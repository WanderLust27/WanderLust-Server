package com.wanderlust.wanderlust_app.auth.interfaces;

import com.wanderlust.wanderlust_app.auth.DTOs.AuthResponseDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.LoginDTO;
import com.wanderlust.wanderlust_app.auth.DTOs.RegisterDTO;

public interface AuthService {
    AuthResponseDTO login(LoginDTO loginDTO);
    AuthResponseDTO register(RegisterDTO registerDTO);
}
