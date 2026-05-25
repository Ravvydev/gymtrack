package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "professor")
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome",  nullable = false)
    private String nome;

    @Column(name = "cpf", length = 11, nullable = false, unique = true)
    private String cpf;

    @Column(name = "idade", nullable = false, length = 2)
    private int idade;

    @Column(name = "sexo",  nullable = false, length = 1)
    private TipoSexoBiologico sexo;

    @Embedded
    private Email email;

    @Embedded
    private Telefone telefone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @OneToMany(mappedBy = "professor",  fetch = FetchType.LAZY)
    private List<Aluno> alunos;

}
