package com.wanderlust.wanderlust_app.auth.DTOs;

import jakarta.validation.constraints.NotNull;

public record LoginDTO(@NotNull String identifier, @NotNull String password ) {
}
