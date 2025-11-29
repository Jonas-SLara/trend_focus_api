package com.ufsm.si.TrendFocus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufsm.si.TrendFocus.dto.request.LoginRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.LoginResponseDTO;
import com.ufsm.si.TrendFocus.infra.security.TokenServiceJWT;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/login")
@Tag(
    name = "Autenticação",
    description = """
    Endpoints para a autenticação e geração do token jwt
""")

public class LoginController {

    private final AuthenticationManager manager;
    private final TokenServiceJWT tokenServiceJWT;

    public LoginController(AuthenticationManager manager, TokenServiceJWT tokenServiceJWT){
        this.manager = manager;
        this.tokenServiceJWT = tokenServiceJWT;
    }

    //implementação da autenticação vai aqui
    @PostMapping
    @Operation(
        summary = "autenticar usuario",
        description = "recebe token jwt do tipo Bearer para identifica-lo nas proximas requisições",
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "Autenticação bem sucedida",
                content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))
            ),
            @ApiResponse(
                responseCode = "401",
                description = "Credênciais inválidas",
                content = @Content
            )
        }
    )
    public ResponseEntity<?> login(
        @Parameter(
            name = "dados",
            required = true,
            description = "dados para autenticação")
        @RequestBody LoginRequestDTO dados){
        
        Authentication authentication = new UsernamePasswordAuthenticationToken(dados.getEmail(), dados.getSenha());
        Authentication aut = manager.authenticate(authentication);

        User usuario = (User) aut.getPrincipal();
        String token = tokenServiceJWT.gerarToken(usuario);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
