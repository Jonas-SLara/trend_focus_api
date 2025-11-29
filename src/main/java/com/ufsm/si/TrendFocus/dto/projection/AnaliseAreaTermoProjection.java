package com.ufsm.si.TrendFocus.dto.projection;

import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;

//projeções por interface que o spring implementa
//Interface-based Projection
public interface AnaliseAreaTermoProjection {
    AreaConhecimentoEnum getAreaConhecimento();
    String getTermo();
    Long getQuantidadeNoticias();
}
