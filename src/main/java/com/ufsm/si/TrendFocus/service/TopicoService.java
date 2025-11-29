package com.ufsm.si.TrendFocus.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ufsm.si.TrendFocus.dto.request.TopicoRequestDTO;
import com.ufsm.si.TrendFocus.dto.response.TopicoResponseDTO;
import com.ufsm.si.TrendFocus.mapper.TopicoMapper;
import com.ufsm.si.TrendFocus.model.Topico;
import com.ufsm.si.TrendFocus.repositories.TopicoRepository;

@Service
public class TopicoService {

    private final TopicoRepository topicoRepository;

    public TopicoService(TopicoRepository topicoRepository){
        this.topicoRepository = topicoRepository;
    }

    public TopicoResponseDTO salvar(TopicoRequestDTO topico){
        Topico novo = TopicoMapper.toEntity(topico);
        return TopicoMapper.toResponse(topicoRepository.save(novo));
    }

    public Page<TopicoResponseDTO> listar(Pageable pageable){
       return topicoRepository.buscarTodos(pageable)
            .map(topico -> TopicoMapper.toResponse(topico));
    }

    public void deletar(String nome){
        Optional<Topico> topico = topicoRepository.findByTitulo(nome);
        if(topico.isPresent()){
            topicoRepository.deleteById(topico.get().getId());
            System.out.println(topico.get().getTitulo() + " removido");
        } else{
            throw new NoSuchElementException(nome + " não encontrado para a operação");
        } 
    }

}
