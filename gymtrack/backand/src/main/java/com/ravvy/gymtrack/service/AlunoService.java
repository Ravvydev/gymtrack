package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.*;
import com.ravvy.gymtrack.dto.mapper.AlunoMapper;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.AlunoRepository;
import com.ravvy.gymtrack.util.CpfValidator;
import com.ravvy.gymtrack.util.Endereco;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    // Dependencia
    private final CpfValidator cpfValidator;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final ProfessorService professorService;
    private final InstituicaoService instituicaoService;
    private final AlunoMapper alunoMapper;

    public AlunoService(CpfValidator cpfValidator, AlunoRepository alunoRepository, EnderecoService enderecoService, ProfessorService professorService, InstituicaoService instituicaoService, AlunoMapper alunoMapper) {
        this.cpfValidator = cpfValidator;
        this.alunoRepository = alunoRepository;
        this.enderecoService = enderecoService;
        this.professorService = professorService;
        this.instituicaoService = instituicaoService;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponse save(AlunoCreateRequest request) {

        if (!cpfValidator.isValid(request.cpf())) {
            throw new IllegalArgumentException("CPF invalid");
        }

        Aluno aluno = toEntity(request);

        Aluno alunoSalvo = alunoRepository.save(aluno);

        return toResponse(alunoSalvo);

    }

    public void deleteById(Long id) {

        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);

    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Aluno não encontrado no id " + id));
    }

    @Transactional
    public AlunoResponse update(AlunoUpdateRequest request, Long id) {

        Aluno aluno = buscarPorId(id);

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Professor professor = professorService.buscarPorId(
                request.professorId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        alunoMapper.updateEntity(
                aluno,
                request,
                endereco,
                professor,
                instituicao
        );

        alunoRepository.save(aluno);

        return toResponse(aluno);
    }

    @Transactional
    public Aluno toEntity(AlunoCreateRequest dto) {

        Endereco endereco = enderecoService.buscarPorId(
                dto.enderecoId());

        Professor professor = professorService.buscarPorId(
                dto.professorId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                dto.instituicaoId());

        return alunoMapper.toEntity(
                dto,
                endereco,
                professor,
                instituicao
        );

    }

    @Transactional
    public void updateSenha(UpdateSenha request,
                            Long idAluno) {

        Aluno aluno = buscarPorId(idAluno);

        if (!aluno.getEmail().getSenha().equals(request.senhaAtual())) {
            throw new IllegalArgumentException(
                    "A senha atual está incorreta");
        }

        aluno.getEmail().setSenha(request.senhaNova());

        alunoRepository.save(aluno);
    }

    @Transactional
    public AlunoResponse toResponse(Aluno aluno) {
        return alunoMapper.toResponse(aluno);
    }

    @Transactional
    public AlunoResponse buscarResponsePorId(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Professor não encontrado no id " + id
                        )
                );

        return alunoMapper.toResponse(aluno);
    }

}
