package com.ufsm.si.TrendFocus.mapper;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import com.ufsm.si.TrendFocus.dto.request.NoticiaRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.NoticiaResponseDTO;
import com.ufsm.si.TrendFocus.dto.response.TermoResponseDTO;
import com.ufsm.si.TrendFocus.model.Noticia;
import com.ufsm.si.TrendFocus.model.Termo;

public class NoticiaMapper {

    public static NoticiaResponseDTO toResponse(Noticia noticia){
        
        //obtendo hashSet de termos com dtos de uma lista
        HashSet<TermoResponseDTO> termos = new HashSet<>();
        Iterator<Termo> i = noticia.getTermos().iterator();
        while(i.hasNext()){
            Termo termo = i.next();
            TermoResponseDTO dto = new TermoResponseDTO(
                termo.getId(),
                termo.getTermo(),
                termo.getTopico().getTitulo(),
                termo.getTopico().getAreaConhecimento().name()
            );
            termos.add(dto);
        }

        return new NoticiaResponseDTO(
            noticia.getId(),
            noticia.getTitulo(),
            noticia.getResumo(),
            noticia.getDataColeta(),
            noticia.getDataPublicacao(),
            noticia.getFonte(),
            noticia.getUrlImagem(),
            noticia.getUrlOriginal(),
            termos
        );
    }

    public static Noticia toEntity(NoticiaRegisterDTO nova, List<Termo> termos){
        Noticia n = new Noticia();
        n.setDataColeta(LocalDateTime.now());
        n.setDataPublicacao(nova.getDataPublicacao());
        n.setFonte(nova.getFonte());
        n.setTitulo(nova.getTitulo());
        n.setResumo(nova.getResumo());
        n.setUrlImagem(nova.getUrlImagem());
        n.setUrlOriginal(nova.getUrlOriginal());
        n.setTermos(termos);
        return n;
    }
}
