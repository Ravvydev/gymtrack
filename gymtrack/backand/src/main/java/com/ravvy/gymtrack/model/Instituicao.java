package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "instituicao")
public class Instituicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String nome;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Embedded
    private Telefone telefone;

    @Embedded
    private Email email;

    @OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY)
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY)
    private List<Professor> professores = new ArrayList<>();
}