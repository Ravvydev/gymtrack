package com.ravvy.gymtrack.aluno.service;

import com.ravvy.gymtrack.aluno.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.aluno.dto.AlunoResponse;
import com.ravvy.gymtrack.aluno.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.aluno.mapper.AlunoMapper;
import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.aluno.repository.AlunoRepository;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.endereco.service.EnderecoService;
import com.ravvy.gymtrack.instituicao.service.InstituicaoService;
import com.ravvy.gymtrack.professor.service.ProfessorService;
import com.ravvy.gymtrack.shared.senhas.dto.UpdateSenhaRequest;
import com.ravvy.gymtrack.shared.util.Cpf;
import com.ravvy.gymtrack.shared.senhas.validation.SenhaValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AlunoService {

    // Dependencia
    private final PasswordEncoder passwordEncoder;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final ProfessorService professorService;
    private final InstituicaoService instituicaoService;
    private final AlunoMapper alunoMapper;

    public AlunoService(PasswordEncoder passwordEncoder, AlunoRepository alunoRepository, EnderecoService enderecoService, ProfessorService professorService, InstituicaoService instituicaoService, AlunoMapper alunoMapper) {
        this.passwordEncoder = passwordEncoder;
        this.alunoRepository = alunoRepository;
        this.enderecoService = enderecoService;
        this.professorService = professorService;
        this.instituicaoService = instituicaoService;
        this.alunoMapper = alunoMapper;
    }

    public AlunoResponse save(AlunoCreateRequest request) {

        SenhaValidator.validar(request.senha());

        Aluno aluno = toEntity(request);

        aluno.setSenhaHash(
                passwordEncoder.encode(request.senha())
        );

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

        Cpf cpf = new Cpf(dto.cpf());

        return alunoMapper.toEntity(
                dto,
                endereco,
                professor,
                instituicao,
                cpf
        );

    }

    @Transactional
    public void updateSenha(UpdateSenhaRequest request,
                            Long idAluno) {

        Aluno aluno = buscarPorId(idAluno);

        if (!passwordEncoder.matches(
                request.senhaAtual(),
                aluno.getSenhaHash())) {

            throw new IllegalArgumentException(
                    "A senha atual está incorreta");
        }

        SenhaValidator.validar(request.senhaNova());

        aluno.setSenhaHash(
                passwordEncoder.encode(request.senhaNova())
        );

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
                                "Aluno não encontrado no id " + id
                        )
                );

        return alunoMapper.toResponse(aluno);
    }

}
