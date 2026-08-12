package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.TipoTesteFisico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "teste_realizado")
public class TesteRealizado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Avaliacao avaliacao;

    @Enumerated(EnumType.STRING)
    private TipoTesteFisico tipoTeste;

    private Double resultadoObtido;

    private String unidadeMedida;
}