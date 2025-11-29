package com.ufsm.si.TrendFocus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
    name = "Termo Chave",
    description = """
    termo chave que se associa a n noticias para depois
    ser o mecanismo da pesquisa e análise de tendências
    por áreas de conhecimento
""")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TermoRequestDTO {

    @NotBlank(message = "nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "nome do tópico é obrigatório")
    private String topico; //nome para o tópico associado
}
