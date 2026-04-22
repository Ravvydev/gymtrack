package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private TipoSexo sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao",  nullable = false)
    private TipoClassificacao tipoClassificacao;

    @Column(name = "DataDaAvaliacao", nullable = false, unique = true)
    private LocalDate dataAvaliacao;

    @Column(name = "peso",  nullable = false)
    private Double peso;

    @Column(name = "altura", nullable = false)
    private Double altura;

    @Column(name = "peso",  nullable = false)
    private Double envergadura;

    @Column(name = "peso",  nullable = false)
    private Double perimetroCintura;

    @Column(name = "zona", nullable = false)
    private TipoClassificacao zona;

    @ManyToOne()
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @OneToMany
    @JoinColumn(name = "lista_exercicios_id")
    private List<Exercicios> exercicios;

}
