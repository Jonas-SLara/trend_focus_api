package com.ufsm.si.TrendFocus.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "noticia")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Noticia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String resumo;
    private String fonte;

    @Column(name = "url_original") 
    private String urlOriginal;

    @Column(name = "url_imagem")
    private String urlImagem;

    @Column(name = "data_publicacao") 
    private LocalDateTime dataPublicacao;

    @Column(name = "data_coleta") 
    private LocalDateTime dataColeta;

    //remover a relação da tabela noticia_termo automaticamente quando a noticia for deletada
    //mas sem remover os termos
    @ManyToMany()
    @JoinTable(
        name = "noticia_termo",
        joinColumns = @JoinColumn(name="noticia_id"),
        inverseJoinColumns = @JoinColumn(name="termo_id")
    )
    private List<Termo> termos;
}
