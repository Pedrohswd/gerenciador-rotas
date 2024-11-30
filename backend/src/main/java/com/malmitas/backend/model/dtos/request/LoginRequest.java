package com.malmitas.backend.model.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Modelo para enviar credenciais de login.")
public class LoginRequest {
    @Schema(description = "Nome de usuário ou email.", example = "usuario@example.com", required = true)
    private String username;

    @Schema(description = "Senha do usuário.", example = "SenhaForte123", required = true)
    private String password;
}
