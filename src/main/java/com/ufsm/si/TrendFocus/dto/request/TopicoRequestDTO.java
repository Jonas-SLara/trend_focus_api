package com.ufsm.si.TrendFocus.dto.request;

import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Schema(
    name = "Tópico de uma área de conhecimento",
    description = """
        um assunto sobre uma determinada área do conhecimento
        exemplo: ENGENHARIAS, CIÊNCIAS_AGRÁRIAS ...
        está relacionado a vários termos chaves
    """
)

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class TopicoRequestDTO {

    @NotBlank(message = "titulo do tópico é obrigatório")
    @NonNull private String titulo;

    private String descricao; //opcional

    @NonNull private AreaConhecimentoEnum areaConhecimento;
}
