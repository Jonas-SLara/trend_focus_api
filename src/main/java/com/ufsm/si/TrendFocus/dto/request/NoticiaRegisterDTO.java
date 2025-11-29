package com.ufsm.si.TrendFocus.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticiaRegisterDTO {
    @Schema(description = "titulo válido para noticia", example = "assunto ... ")
    @NotBlank
    private String titulo;
   
    @Schema(description = "resumo para notícia em poucas linhas", example = "resumo ...")
    @NotBlank
    private String resumo;
    
    @Schema(description = "data que foi publicada", example = "2025-10-27T03:54:05.785Z")
    private LocalDateTime dataPublicacao;
    
    @Schema(description = "fonte de pesquisa de onde veio a noticia", example = "el pais")
    @NotBlank
    private String fonte;

    @Schema(
        description = "url da imagem principal da noticia",
        example = "https://caminho/img.png"
    )
    private String urlImagem;
    
    @Schema(
        description = "url da fonte principal da noticia",
        example = "https://caminho/fonte.png"
    )
    private String urlOriginal;

    @Schema(
        description = "lista de ids de termos para a noticia",
        examples = {"1", "2","3"}
    )
    //será passado uma lista de termos chaves
    private List<Long> termoId; 
}
