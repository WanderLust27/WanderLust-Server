package com.wanderlust.wanderlust_app.auth.DTOs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RegisterDTO(@NotNull String username , @NotNull String password , @NotNull String email , @NotNull String firstName , @NotNull String familyName , @NotNull @Min(10) @Max(13) String phoneNumber ) {
}
