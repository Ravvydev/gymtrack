package com.ravvy.gymtrack.endereco.dto;

import com.ravvy.gymtrack.aluno.repository.AlunoRepository;
import com.ravvy.gymtrack.instituicao.repository.InstituicaoRepository;
import com.ravvy.gymtrack.professor.repository.ProfessorRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Garante que um endereço só seja usado por UM cadastro (aluno, professor ou instituição).
 * Sem isso, quem se cadastra poderia informar o id do endereço de outra pessoa
 * e receber esse endereço de volta na resposta do cadastro.
 */
@Component
public class EnderecoVinculoValidator {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final InstituicaoRepository instituicaoRepository;

    public EnderecoVinculoValidator(AlunoRepository alunoRepository,
                                    ProfessorRepository professorRepository,
                                    InstituicaoRepository instituicaoRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    @Transactional(readOnly = true)
    public void garantirDisponivel(Long enderecoId) {
        boolean emUso = alunoRepository.existsByEnderecoId(enderecoId)
                || professorRepository.existsByEnderecoId(enderecoId)
                || instituicaoRepository.existsByEnderecoId(enderecoId);

        if (emUso) {
            throw new IllegalArgumentException("Endereço indisponível");
        }
    }
}