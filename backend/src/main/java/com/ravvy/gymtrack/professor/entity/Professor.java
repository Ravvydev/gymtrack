package com.ravvy.gymtrack.professor.entity;

import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.shared.util.Cpf;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.shared.util.Telefone;
import com.ravvy.gymtrack.aluno.enums.TipoSexoBiologico;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
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

    @Embedded
    private Cpf cpf;

    @Column(name = "data_Nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false)
    private TipoSexoBiologico sexo;

    @Embedded
    private Email email;

    @Column(name = "senha_hash", nullable = false, length = 60)
    private String senhaHash;

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
