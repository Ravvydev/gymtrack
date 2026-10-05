package com.ravvy.gymtrack.instituicao.entity;

import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.shared.util.Telefone;
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

    @Column(name = "senha_hash", nullable = false, length = 60)
    private String senhaHash;

    @OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY)
    private List<Aluno> alunos = new ArrayList<>();

    @OneToMany(mappedBy = "instituicao", fetch = FetchType.LAZY)
    private List<Professor> professores = new ArrayList<>();
}