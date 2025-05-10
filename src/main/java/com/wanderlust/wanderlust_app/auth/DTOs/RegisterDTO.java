package com.wanderlust.wanderlust_app.auth.DTOs;


import com.wanderlust.wanderlust_app.auth.enums.UserType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record RegisterDTO(@NotNull String username , @NotNull String password , @NotNull String email , @NotNull String firstName , @NotNull String familyName , @NotNull @Pattern(regexp = "^\\+?[1-9]\\d{9,14}$") String phoneNumber , @NotNull UserType userType ) {
}
