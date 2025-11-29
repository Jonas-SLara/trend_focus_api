package com.ufsm.si.TrendFocus.dto.response;

import java.time.LocalDateTime;
import java.util.HashSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaResponseDTO {
    private Long id; //nao ser visivel no front, mas facilita remoção
    private String titulo;
    private String resumo;
    private LocalDateTime dataColeta;
    private LocalDateTime dataPublicacao;
    private String fonte;
    private String urlImagem;
    private String urlOriginal;
    private HashSet<TermoResponseDTO> termos;// nome, topico, areaConhecimento
}
