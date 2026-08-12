package com.ravvy.gymtrack.model;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "aluno")
public class Aluno{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @Column(name = "data_nascimento", nullable = false)
    private LocalDate dataNascimento;

    @Column(name = "sexo", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoSexoBiologico sexo;

    @Embedded
    private Email email;

    @Embedded
    private Telefone telefone;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(name = "cpf", length = 11,
            nullable = false, unique = true)
    private String cpf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor = null;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "instituicao_id")
    private Instituicao instituicao;

    @OneToMany(mappedBy = "aluno", fetch = FetchType.LAZY)
    private List<Avaliacao> avaliacoes;

}
