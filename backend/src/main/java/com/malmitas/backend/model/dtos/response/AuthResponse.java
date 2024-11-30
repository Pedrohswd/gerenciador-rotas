package com.malmitas.backend.model.dtos.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o token JWT após autenticação.")
public class AuthResponse {
    @Schema(description = "Token JWT gerado após autenticação.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6...")
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
