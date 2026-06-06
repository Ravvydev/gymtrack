package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.TipoTestFisico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Exercicios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private List<TipoTestFisico> exercicios;

}
