package com.ufsm.si.TrendFocus.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TermoResponseDTO {
    private Long id;
    private String termo;
    private String topico;
    private String areaConhecimento;
}
