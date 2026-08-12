package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
import com.ravvy.gymtrack.dto.mapper.InstituicaoMapper;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.AlunoRepository;
import com.ravvy.gymtrack.repository.InstituicaoRepository;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import com.ravvy.gymtrack.util.Endereco;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstituicaoService {

    private final InstituicaoRepository instituicaoRepository;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final InstituicaoMapper instituicaoMapper;
    private final ProfessorRepository professorRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository, EnderecoService enderecoService, InstituicaoMapper instituicaoMapper, ProfessorRepository professorRepository, AlunoRepository alunoRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.enderecoService = enderecoService;
        this.instituicaoMapper = instituicaoMapper;
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

    public List<Aluno> listarTodosAlunosInstituicao(Long idInstituicao) {

        Instituicao instituicao = buscarPorId(idInstituicao);

        return instituicao.getAlunos();

    }

    public List<Professor> listarTodosProfessoresInstituicao(Long idInstituicao) {
        Instituicao instituicao = buscarPorId(idInstituicao);

        return instituicao.getProfessores();
    }


    public List<Instituicao> listarAllInstituicoes() {
        return instituicaoRepository.findAll();
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
    public void updateSenha(UpdateSenha request,
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
        ;
        return instituicaoMapper.toResponse(instituicao);
    }

}