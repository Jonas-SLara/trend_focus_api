package com.ufsm.si.TrendFocus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufsm.si.TrendFocus.dto.request.UsuarioRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.UsuarioDTO;
import com.ufsm.si.TrendFocus.service.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
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

    //endpoint de acesso público para se cadastrar como ROLE_ANALISTA
    @Operation(
        summary = "criar novo usuário",
        description = "cria um usuário com a role de ANALISTA por padrão",
        responses = {
            @ApiResponse(
                responseCode = "201",
                description = "Usuário criado com sucesso",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
            ),
            @ApiResponse(responseCode = "400", description = "dados inválidos"),
            @ApiResponse(responseCode = "409", description = "email já cadastrado")
        }
    )
    @PostMapping
    @Transactional
    public ResponseEntity<?> criar(@RequestBody @Valid UsuarioRegisterDTO ur, UriComponentsBuilder uriBuilder) {       
        UsuarioDTO ud = this.service.salvar(ur);
        
        //isso aqui cria a url do novo recurso de usuario e devolver no cabeçalho location da resposta
        URI uri = uriBuilder.path("/usuario/{id}")
        .buildAndExpand(ud.getId()).toUri();

        return ResponseEntity.created(uri).body(ud);
    }

    @SecurityRequirement(name = "bearerAuth")
    @Operation(
        summary = "listar usuarios no sistema",
        description = """
            Lista de todos os usuários no sistema
            Apenas para usuários com a ROLE_ADM
        """,
        responses = {
            @ApiResponse(
                responseCode = "200",
                description = "lista retornada com sucesso",
                content = @Content(schema = @Schema(implementation = UsuarioDTO.class))
            ),
            @ApiResponse(
                responseCode = "403",
                description = "acesso negado"
            )
        }
    )
    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok().body(this.service.listar());
    }
}
