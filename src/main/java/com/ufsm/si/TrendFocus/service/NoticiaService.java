package com.ufsm.si.TrendFocus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufsm.si.TrendFocus.dto.request.NoticiaRegisterDTO;
import com.ufsm.si.TrendFocus.dto.response.AnaliseAreaTermoResponseDTO;
import com.ufsm.si.TrendFocus.dto.response.NoticiaResponseDTO;
import com.ufsm.si.TrendFocus.mapper.NoticiaMapper;
import com.ufsm.si.TrendFocus.model.Noticia;
import com.ufsm.si.TrendFocus.model.Termo;
import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;
import com.ufsm.si.TrendFocus.repositories.NoticiaRepository;
import com.ufsm.si.TrendFocus.repositories.TermoRepository;

@Service
public class NoticiaService {

    private final NoticiaRepository noticiaRepository;
    private final TermoRepository termoRepository;

    public NoticiaService(
        NoticiaRepository noticiaRepository,
        TermoRepository termoRepository){
        this.noticiaRepository = noticiaRepository;
        this.termoRepository = termoRepository;
    }

    @Transactional
    public NoticiaResponseDTO salvar(NoticiaRegisterDTO nova){
        //busca todos os termos pelo id passado
        List<Termo> termos = termoRepository.findAllById(nova.getTermoId());
        System.out.println("\n" + termos.get(0).getTermo() + "\n");

        if(termos.isEmpty()){
            throw new IllegalArgumentException("nenhum id de termo válido");
        }

        Noticia noticia = NoticiaMapper.toEntity(nova, termos);
        Noticia entidade = noticiaRepository.save(noticia);
        return NoticiaMapper.toResponse(entidade);   
    }

    //obtive uma lista de objetos com os campos dejesados de uma query
    //usei uma interface que o spring implementa em tempo de execução e obtive os dto
    public List<AnaliseAreaTermoResponseDTO> gerarAnalise(LocalDateTime inicio, LocalDateTime fim) {
        return noticiaRepository.analisePorAreaTermo(inicio, fim)
            .stream()
            .map(p -> new AnaliseAreaTermoResponseDTO(
                    p.getAreaConhecimento(),
                    p.getTermo(),
                    p.getQuantidadeNoticias()
            ))
            .toList();
    }

    public Page<NoticiaResponseDTO> listar(AreaConhecimentoEnum area, Long termoId, Pageable pageable){
        return noticiaRepository.buscarTodos(area, termoId, pageable)
            .map(n -> NoticiaMapper.toResponse(n));
    }

    public Page<NoticiaResponseDTO> listarPorPeriodo(
        AreaConhecimentoEnum area,
        Long termoId,
        LocalDateTime inicio,
        LocalDateTime fim,
        Pageable pageable
    ){
        return noticiaRepository.buscarTodosPorPeriodo(area, termoId, inicio, fim, pageable)
            .map(n -> NoticiaMapper.toResponse(n));
    }

    @Transactional
    public void deletar(Long id){
        if(noticiaRepository.existsById(id)){
            noticiaRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("noticia com id: " + id + " não encontrada para operação");
        }
    }
}
