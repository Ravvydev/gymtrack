package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.*;
import com.ravvy.gymtrack.dto.mapper.AlunoMapper;
import com.ravvy.gymtrack.dto.mapper.InstituicaoMapper;
import com.ravvy.gymtrack.dto.mapper.ProfessorMapper;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.AlunoRepository;
import com.ravvy.gymtrack.repository.InstituicaoRepository;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import com.ravvy.gymtrack.model.Endereco;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final InstituicaoMapper instituicaoMapper;
    private final ProfessorRepository professorRepository;
    private final AlunoMapper alunoMapper;
    private final ProfessorMapper professorMapper;

    public InstituicaoService(InstituicaoRepository instituicaoRepository, EnderecoService enderecoService, InstituicaoMapper instituicaoMapper, ProfessorRepository professorRepository, AlunoRepository alunoRepository, AlunoMapper alunoMapper, ProfessorMapper professorMapper) {
        this.instituicaoRepository = instituicaoRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.enderecoService = enderecoService;
        this.instituicaoMapper = instituicaoMapper;
        this.alunoMapper = alunoMapper;
        this.professorMapper = professorMapper;
    }

    @Transactional
    public InstituicaoResponse save(InstituicaoCreateRequest request) {

        Instituicao instituicao = toEntity(request);

        Instituicao instituicaoSalva =
                instituicaoRepository.save(instituicao);

        return toResponse(instituicaoSalva);
    }

    public void deleteById(Long id) {
        Instituicao instituicao = buscarPorId(id);
        instituicaoRepository.delete(instituicao);
    }

    @Transactional
    public void vincularAlunoInstituicao(Long idInstituicao, Long idAluno) {

        Instituicao instituicao = buscarPorId(idInstituicao);
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(
                () -> new EntityNotFoundException("Aluno não encontrado no id " + idAluno)
        );

        if (aluno.getInstituicao() != null) {
            throw new IllegalArgumentException(
                    "O aluno já está vinculado a instituição: " + aluno.getInstituicao().getNome()
            );
        }

        instituicao.getAlunos().add(aluno);
        aluno.setInstituicao(instituicao);
        instituicaoRepository.save(instituicao);
    }

    @Transactional
    public void vincularProfessorInstituicao(Long idInstituicao,
                                             Long idProfessor) {

        Instituicao instituicao = buscarPorId(idInstituicao);
        Professor professor = professorRepository.findById(idProfessor).orElseThrow(
                () -> new EntityNotFoundException("Professor não encontrado no id " + idProfessor)
        );

        if (professor.getInstituicao() != null) {
            throw new IllegalArgumentException(
                    "O professor já está vinculado a instituição: " + professor.getInstituicao().getNome()
            );
        }

        instituicao.getProfessores().add(professor);
        professor.setInstituicao(instituicao);
        instituicaoRepository.save(instituicao);
    }

    @Transactional(readOnly = true)
    public List<AlunoResponse> listarTodosAlunosInstituicao(Long idInstituicao) {

        Instituicao instituicao = buscarPorId(idInstituicao);

        List<Aluno> allAlunos = instituicao.getAlunos();
        List<AlunoResponse> alunosResponse = new ArrayList<>();

        for (Aluno aluno : allAlunos) {

            AlunoResponse alunoResponse = alunoMapper.toResponse(aluno);
            alunosResponse.add(alunoResponse);

        }

        return alunosResponse;

    }

    @Transactional(readOnly = true)
    public List<ProfessorResponse> listarTodosProfessoresInstituicao(Long idInstituicao) {

        Instituicao instituicao = buscarPorId(idInstituicao);
        List<Professor> allProfessores = instituicao.getProfessores();
        List<ProfessorResponse> professoresResponse = new ArrayList<>();

        for (Professor professor : allProfessores) {
            ProfessorResponse professorResponse = professorMapper.toResponse(professor);
            professoresResponse.add(professorResponse);
        }
        return professoresResponse;
    }

    @Transactional(readOnly = true)
    public List<InstituicaoResponse> listarAllInstituicoes() {

        List<Instituicao> allInstituicoes = instituicaoRepository.findAll();
        List<InstituicaoResponse> instituicoesResponse = new ArrayList<>();

        for (Instituicao instituicao : allInstituicoes) {
            InstituicaoResponse instituicaoResponse =
                    instituicaoMapper.toResponse(instituicao);
            instituicoesResponse.add(instituicaoResponse);
        }

        return instituicoesResponse;
    }

    public Instituicao buscarPorId(Long id) {
        return instituicaoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Instituição não encontrada no id " + id));
    }

    @Transactional
    public InstituicaoResponse update(InstituicaoUpdateRequest request, Long id) {

        Instituicao instituicao = buscarPorId(id);

        Endereco endereco = enderecoService.buscarPorId(request.enderecoId());

        instituicaoMapper.updateEntity(
                request,
                instituicao,
                endereco);

        instituicaoRepository.save(instituicao);
        return instituicaoMapper.toResponse(instituicao);
    }

    @Transactional
    public void updateSenha(UpdateSenhaRequest request,
                            Long id) {

        Instituicao instituicao = buscarPorId(id);

        if (!instituicao.getEmail().getSenha().equals(request.senhaAtual())) {
            throw new IllegalArgumentException(
                    "A senha atual está incorreta");
        }

        instituicao.getEmail().setSenha(request.senhaNova());

    }

    @Transactional
    public Instituicao toEntity(InstituicaoCreateRequest request) {

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        return instituicaoMapper.toEntity(
                request,
                endereco
        );

    }

    @Transactional
    public InstituicaoResponse toResponse(Instituicao instituicao) {
        return instituicaoMapper.toResponse(instituicao);
    }

    @Transactional(readOnly = true)
    public InstituicaoResponse buscarResponsePorId(Long id) {

        Instituicao instituicao = instituicaoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                                "Instituição não encontrada no id " + id
                        )
                );

        return instituicaoMapper.toResponse(instituicao);
    }

}