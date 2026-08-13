package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.TipoClassificacao;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(name = "idade")
    private Integer idade;

    @ManyToOne
    @JoinColumn(name = "professor_id")
    private Professor professor;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_Aluno", nullable = false)
    private TipoSexoBiologico sexo;

    @Column(name = "DataDaAvaliacao", nullable = false)
    private LocalDate dataAvaliacao;

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
    private TipoClassificacao classificacaoImc;

    @Enumerated(EnumType.STRING)
    @Column(name = "classificacao_rce", nullable = false)
    private TipoClassificacao classificacaoRce;

    @OneToMany(
            mappedBy = "avaliacao",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<TesteRealizado> testesRealizados = new ArrayList<>();

}
