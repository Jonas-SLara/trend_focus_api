package com.ufsm.si.TrendFocus.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufsm.si.TrendFocus.dto.request.TermoRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.TermoResponseDTO;
import com.ufsm.si.TrendFocus.mapper.TermoMapper;
import com.ufsm.si.TrendFocus.model.Termo;
import com.ufsm.si.TrendFocus.model.Topico;
import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;
import com.ufsm.si.TrendFocus.repositories.TermoRepository;
import com.ufsm.si.TrendFocus.repositories.TopicoRepository;

@Service
public class TermoService {

    private final TermoRepository termoRepository;
    private final TopicoRepository topicoRepository;

    public TermoService(
            TermoRepository termoRepository,
            TopicoRepository topicoRepository){
        this.termoRepository = termoRepository;
        this.topicoRepository = topicoRepository;
    }

    public TermoResponseDTO salvar(TermoRequestDTO termo){
        Optional<Topico> topico = topicoRepository.findByTitulo(termo.getTopico());
        if(!topico.isPresent()){
             throw new NoSuchElementException("topico: " + termo.getTopico() + " não encontrado");
        }
        Termo novo =  TermoMapper.toEntity(termo, topico.get());
        return TermoMapper.toResponse(termoRepository.save(novo));
    }

    public Page<TermoResponseDTO> listar(AreaConhecimentoEnum area, Pageable pageable){
        return termoRepository.buscarTodos(area, pageable)
            .map(termo -> TermoMapper.toResponse(termo));
    }

    public void deletar(String nome){
        Optional<Termo> termo = termoRepository.findByTermo(nome);
        if(termo.isPresent()){
            termoRepository.deleteById(termo.get().getId());
            System.out.println("termo: " + termo.get().getTermo() + " deletado");
        } else{
            throw new NoSuchElementException(nome + " não encontrado para operação");
        }
    }
}
