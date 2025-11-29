package com.ufsm.si.TrendFocus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRegisterDTO {
    @Schema(description = "nome de usuario", example = "jonas")
    @NotBlank(message="nome é obrigatório")
    @Size(min=3, max=50, message = "nome deve conter de 3 a 50 char")
    private String nome;

    @Schema(description = "senha para o usuario", example = "1234hash")
    @NotBlank(message = "senha é obrigatória")
    @Size(min = 6, message = "senha deve conter no mínimo 6 char")
    private String senha;

    @Schema(description = "email institucional", example = "jonas@ufsm.br")
    @Email(message = "email inválido")
    @NotBlank
    private String email;
}
