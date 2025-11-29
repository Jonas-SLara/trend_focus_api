package com.ufsm.si.TrendFocus.dto.response;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class TopicoResponseDTO {
    @NonNull private String titulo;

    private String descricao; //opcional

    @NonNull private String areaConhecimento;
    
    //lista de termos associados
    @NonNull private List<String> termos;
}
