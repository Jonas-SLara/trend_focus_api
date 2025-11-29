package com.ufsm.si.TrendFocus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ufsm.si.TrendFocus.dto.request.TermoRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.TermoResponseDTO;
import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;
import com.ufsm.si.TrendFocus.service.TermoService;

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
@RequestMapping("/termo")
@Tag(
    name = "Termos Chave",
    description = """
        Endpoints relacionados a criação e remoção de termos chaves
        e também a listagem de termos seguindo filtros por area de conhecimento
    """)
public class TermoController {

    private final TermoService termoService;

    public TermoController(TermoService termoService){
        this.termoService = termoService;
    }

    @GetMapping
    public ResponseEntity<Page<TermoResponseDTO>> listar(
            @RequestParam(required = false) AreaConhecimentoEnum area,
            Pageable pageable) {
        return ResponseEntity.ok().body(
            termoService.listar(area, pageable)
        );
    }
    
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<TermoResponseDTO> salvar(
            @RequestBody @Valid TermoRequestDTO novo,
            UriComponentsBuilder uBuilder) {

        TermoResponseDTO termo = termoService.salvar(novo);

        URI location = uBuilder.path("/termo/{nome}")
            .buildAndExpand(termo.getTermo())
            .toUri();

        return ResponseEntity.created(location).body(termo);
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping
    public ResponseEntity<?> deletar(@RequestParam String nome){
        termoService.deletar(nome);
        return ResponseEntity.noContent().build();
    }
}
