package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @Column(name = "idade_aluno")
    private Integer idadeAluno;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_Aluno", nullable = false)
    private TipoSexoBiologico sexo;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao",  nullable = false)
    private TipoClassificacao tipoClassificacao;

    @Column(name = "DataDaAvaliacao", nullable = false)
    private LocalDateTime dataAvaliacao;

    @Column(name = "peso",  nullable = false)
    @NotNull
    @Positive
    private Double peso;

    @Column(name = "altura", nullable = false)
    @NotNull
    @Positive
    private Double altura;

    @Column(name = "evergadura",  nullable = false)
    @NotNull
    @Positive
    private Double envergadura;

    @Column(name = "perimetroCintura",  nullable = false)
    @NotNull
    @Positive
    private Double perimetroCintura;

    @Column(name = "IMC", nullable = false)
    @NotNull
    @Positive
    private Double imc;

    @Column(name = "RCE", nullable = false)
    @NotNull
    @Positive
    private Double rce;

    @Enumerated(EnumType.STRING)
    @Column(name = "zona", nullable = false)
    private TipoClassificacao zona;

    @OneToMany
    @JoinColumn(name = "lista_exercicios_id")
    private List<Exercicios> exercicios;

}
