package com.ufsm.si.TrendFocus.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ufsm.si.TrendFocus.dto.projection.AnaliseAreaTermoProjection;
import com.ufsm.si.TrendFocus.model.Noticia;
import com.ufsm.si.TrendFocus.model.enums.AreaConhecimentoEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface NoticiaRepository extends JpaRepository<Noticia, Long>{

    /*
      buscar todas as noticias com paginacao e com filtros opcionais, 
      :area -> for nulo não leva em conta o filtro na pesquisa
      :termoId -> for nulo não leva em conta o termo
    */
    @Query("""
        SELECT DISTINCT n FROM Noticia n
        JOIN n.termos t
        JOIN t.topico tp
        WHERE (:termoId IS NULL OR t.id = :termoId)
         AND (:area IS NULL OR tp.areaConhecimento = :area)
        """)
    Page<Noticia> buscarTodos(
        @Param("area") AreaConhecimentoEnum area,
        @Param("termoId") Long id,
        Pageable pageable
    );

    /*
      buscar noticias em um periodo determinado, (ultimos 7, 30 dias, ultimo ano)
      filtros opcionais para areas e termos
    */
    @Query("""
        SELECT n FROM Noticia n
        JOIN n.termos t
        JOIN t.topico tp
        WHERE n.dataPublicacao BETWEEN :inicio AND :fim
            AND (:area IS NULL OR tp.areaConhecimento = :area)
            AND (:termoId IS NULL OR t.id = :termoId)
        """)
    Page<Noticia> buscarTodosPorPeriodo(
        @Param("area") AreaConhecimentoEnum area,
        @Param("termoId") Long id,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim,
        Pageable pageable
    );

    /*
     pesquisa para analise de dados, retornar a quantidade de noticias
     em cada area de conhecimento e termos associados, de forma paginas
     por um periodo de tempo específico
    */
    @Query("""
    SELECT tp.areaConhecimento AS areaConhecimento,
           t.termo AS termo,
           COUNT(n.id) AS quantidadeNoticias
    FROM Noticia n
    JOIN n.termos t
    JOIN t.topico tp
    WHERE n.dataPublicacao BETWEEN :inicio AND :fim
    GROUP BY tp.areaConhecimento, t.termo
    ORDER BY tp.areaConhecimento, COUNT(n.id) DESC
    """)
    List<AnaliseAreaTermoProjection> analisePorAreaTermo(
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );


    //permite buscar por titulo
    Optional<Noticia> findByTitulo(String titulo);

    //permite buscar pela url
    Optional<Noticia>  findByUrlOriginal(String urlOriginal);
} 
