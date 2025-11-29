package com.ufsm.si.TrendFocus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufsm.si.TrendFocus.dto.request.TopicoRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.TopicoResponseDTO;
import com.ufsm.si.TrendFocus.service.TopicoService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/topico")
@Tag(
    name = "Topicos de Conhecimento",
    description = """
        Coleção de endpoints para criar e remover tópicos,
        e também listar por paginação seguindo filtros por
        area de conhecimento
    """)
public class TopicoController {

    private final TopicoService topicoService;

    public TopicoController(TopicoService topicoService){
        this.topicoService = topicoService;
    }

    //GET http://localhost:3000/topico?page=0&size=3
    @GetMapping
    public ResponseEntity<Page<TopicoResponseDTO>> listar(Pageable pageable) {
        return ResponseEntity.ok().body(topicoService.listar(pageable));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<TopicoResponseDTO> salvar(
            @RequestBody @Valid TopicoRequestDTO novo,
            UriComponentsBuilder uBuilder) {

        TopicoResponseDTO topico = topicoService.salvar(novo);
        URI location = uBuilder.path("/topico/{nome}")
                .buildAndExpand(topico.getTitulo())
                .toUri();
        return ResponseEntity.created(location).body(topico);
    }
    
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping
    public ResponseEntity<?> deletar(@RequestParam String nome){
        topicoService.deletar(nome);
        return ResponseEntity.noContent().build();
    }
}
