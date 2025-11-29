package com.ufsm.si.TrendFocus.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ufsm.si.TrendFocus.model.Topico;
import java.util.List;
import java.util.Optional;

import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;
@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long>{

    //buscar tópicos por area de conhecimento
    
    @Query("SELECT t FROM Topico t LEFT JOIN FETCH t.termos WHERE t.areaConhecimento = :area")
    List<Topico> buscarPorArea(@Param("areaConhecimento") AreaConhecimentoEnum area);

    @Query("SELECT t FROM Topico t LEFT JOIN FETCH t.termos WHERE t.id = :id")
    Optional<Topico> buscarPorId(@Param("id") Long ind);

    //busc com paginacao
    @Query("SELECT t FROM Topico t LEFT JOIN FETCH t.termos")
    Page<Topico> buscarTodos(Pageable pageable);

    Optional<Topico> findByTitulo(String titulo);

    List<Topico> findByTituloContainingIgnoreCase(String titulo);
} 
