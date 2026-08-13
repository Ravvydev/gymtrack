package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.*;
import com.ravvy.gymtrack.dto.mapper.ProfessorMapper;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.AlunoRepository;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import com.ravvy.gymtrack.model.Endereco;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final InstituicaoService instituicaoService;
    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, AlunoRepository alunoRepository, EnderecoService enderecoService, InstituicaoService instituicaoService, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.enderecoService = enderecoService;
        this.instituicaoService = instituicaoService;
        this.professorMapper = professorMapper;
    }

    public ProfessorResponse save(ProfessorCreateRequest request) {

        Professor professor = toEntity(request);
        Professor professorSalvo = professorRepository.save(professor);
        return toResponse(professorSalvo);

    }

    public void deleteById(Long id) {
        Professor professor = buscarPorId(id);
        professorRepository.delete(professor);
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Professor não encontrado no id " + id));
    }

    @Transactional
    public ProfessorResponse update(ProfessorUpdateRequest request,
                                    Long id) {

        Professor professor = buscarPorId(id);

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        professorMapper.updateEntity(
                request,
                professor,
                endereco,
                instituicao
        );

        professorRepository.save(professor);

        return professorMapper.toResponse(professor);
    }

    @Transactional
    public UpdateSenhaResponse updateSenha(UpdateSenhaRequest request,
                                           Long id) {
        Professor professor = buscarPorId(id);

        if (!professor.getEmail().getSenha().equals(request.senhaAtual())){
            throw new IllegalArgumentException(
                    "A senha atual está incorreta");
        }

        professor.getEmail().setSenha(request.senhaNova());

        return new UpdateSenhaResponse(
                request.senhaNova()
        );
    }

    @Transactional
    public Professor toEntity(ProfessorCreateRequest request) {

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        return professorMapper.toEntity(
                request,
                endereco,
                instituicao
        );

    }
    @Transactional
    public ProfessorResponse toResponse(Professor professor) {
        return professorMapper.toResponse(professor);
    }

    @Transactional
    public void vincularAluno(Long idProfessor,
                              Long idAluno) {

        Professor professor = buscarPorId(idProfessor);
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(
                () -> new EntityNotFoundException("Aluno não encontrado no id " + idAluno)
        );

        if (aluno.getProfessor() != null) {
            throw new IllegalArgumentException(
                    "O aluno já está vinculado ao professor"
            );
        }

        professor.getAlunos().add(aluno);
        aluno.setProfessor(professor);

    }

    @Transactional
    public ProfessorResponse buscarResponsePorId(Long id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Professor não encontrado no id " + id
                        )
                );

        return professorMapper.toResponse(professor);
    }

}