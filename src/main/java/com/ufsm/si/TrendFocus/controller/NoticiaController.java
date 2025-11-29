package com.ufsm.si.TrendFocus.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufsm.si.TrendFocus.dto.request.NoticiaRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.AnaliseAreaTermoResponseDTO;
import com.ufsm.si.TrendFocus.dto.response.NoticiaResponseDTO;
import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;
import com.ufsm.si.TrendFocus.service.NoticiaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/noticia")
@Tag(
    name = "Notícias",
    description = """
        Coleção de endpoints que permite inserir e remover noticias,
        gerenciar informações principais de noticias individuais como:
        mudar urls, titulo, e termos chaves.
        Listar por paginação noticias seguindo períodos de tempo e também
        filtros opcionais como termos e area de conhecimento
    """)
public class NoticiaController {

    private final NoticiaService noticiaService;

    public NoticiaController(NoticiaService noticiaService){
        this.noticiaService = noticiaService;
    }

    @GetMapping
    public ResponseEntity<Page<NoticiaResponseDTO>> listar(
            @RequestParam(required = false) AreaConhecimentoEnum area,
            @RequestParam(required = false) Long termoId,
            Pageable pageable) {
        Page<NoticiaResponseDTO> page = noticiaService.listar(area, termoId, pageable);
        return ResponseEntity.ok().body(page);
    }

     @GetMapping("/analise")
    public ResponseEntity<List<AnaliseAreaTermoResponseDTO>> analisar(
        @RequestParam LocalDateTime inicio
    ) {
        return ResponseEntity.ok(noticiaService.gerarAnalise(inicio, LocalDateTime.now()));
    }

    //considera o período das noticias para o filtro
    @GetMapping("/recentes")
    public ResponseEntity<Page<NoticiaResponseDTO>> listarRecentes(
        @RequestParam(required = false) AreaConhecimentoEnum area,
        @RequestParam(required = false) Long termoId,
        @RequestParam(required = true) LocalDateTime inicio,
        Pageable pageable) {
        Page<NoticiaResponseDTO> page = noticiaService.listarPorPeriodo(
            area, termoId, inicio, LocalDateTime.now(), pageable
        );
        return ResponseEntity.ok().body(page);
    }
    

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping
    public ResponseEntity<NoticiaResponseDTO> salvar(@RequestBody @Valid NoticiaRegisterDTO dto ) {
        NoticiaResponseDTO noticia = this.noticiaService.salvar(dto);
        return ResponseEntity.ok().body(noticia);
    }
    
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping ResponseEntity<?> deletar(@RequestParam Long id){
        this.noticiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
