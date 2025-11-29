package com.ufsm.si.TrendFocus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LoginRequestDTO {
    
    @Schema(description = "email institucional para autenticação", example = "jonas@ufsm.br")
    @Email(message = "formato de email inválido")
    private String email;

    @Schema(description = "senha do usuario que será criptografada", example = "1234hash")
    @Size(min = 6, message = "senha deve ter no minimo 6 char")
    private String senha;
}
