package com.ufsm.si.TrendFocus.dto.response;

import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;

public record AnaliseAreaTermoResponseDTO(
    AreaConhecimentoEnum area,
    String termo,
    Long quantidade
) {}
