package com.ufsm.si.TrendFocus.mapper;

import com.ufsm.si.TrendFocus.model.Topico;

import java.util.List;

import com.ufsm.si.TrendFocus.dto.request.TopicoRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.TopicoResponseDTO;

public class TopicoMapper {

    public static Topico toEntity(TopicoRequestDTO t){
        Topico novo = new Topico();
        novo.setAreaConhecimento(t.getAreaConhecimento());
        novo.setDescricao(t.getDescricao());
        novo.setTitulo(t.getTitulo());
        return novo;
    }

    public static TopicoResponseDTO toResponse(Topico topico){
        List<String> termos = (topico.getTermos()==null)
        ? List.of()
        : topico.getTermos().stream().map(topic-> topic.getTermo()).toList();
        return new TopicoResponseDTO(
            topico.getTitulo(),
            topico.getDescricao(),
            topico.getAreaConhecimento().name(),
            termos
        );
    }
}

