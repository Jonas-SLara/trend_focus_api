package com.ufsm.si.TrendFocus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufsm.si.TrendFocus.dto.request.UsuarioRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.UsuarioDTO;
import com.ufsm.si.TrendFocus.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/usuario")
@Tag(
    name = "Usuarios", 
    description = """
        criação, listagem e gerenciamento de usuarios, edição de
        dados pessoais e remoção de contas que não sejam ADM
    """)
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service){
        this.service = service;
    }

    @Operation(
        summary = "criar novo usuário",
        description = "cria um usuário com a role de ANALISTA por padrão"
    )
    @SecurityRequirement(name = "jwt_auth")
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid UsuarioRegisterDTO ur, UriComponentsBuilder uriBuilder) {       
        UsuarioDTO ud = this.service.salvar(ur);
        URI uri = uriBuilder.path("/usuario/{id}")
        .buildAndExpand(ud.getId()).toUri();
        return ResponseEntity.created(uri).body(ud);
    }

    @SecurityRequirement(name = "jwt_auth")
    @Operation(summary = "listar usuarios")
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(this.service.listar());
    }
}
