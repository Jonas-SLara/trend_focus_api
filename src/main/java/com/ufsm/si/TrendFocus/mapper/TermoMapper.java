package com.ufsm.si.TrendFocus.mapper;

import com.ufsm.si.TrendFocus.dto.request.TermoRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.TermoResponseDTO;
import com.ufsm.si.TrendFocus.model.Termo;
import com.ufsm.si.TrendFocus.model.Topico;

public class TermoMapper {

    public static Termo toEntity(TermoRequestDTO termo, Topico topico){
        Termo novo = new Termo();
        novo.setTermo(termo.getNome());
        novo.setTopico(topico);
        return novo;
    }

    public static TermoResponseDTO toResponse(Termo termo){
        return new TermoResponseDTO(
            termo.getId(),
            termo.getTermo(),
            termo.getTopico().getTitulo(),
            termo.getTopico().getAreaConhecimento().name()
        );
    }
}
