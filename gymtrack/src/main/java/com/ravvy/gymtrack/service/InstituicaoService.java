package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.AlunoRepository;
import com.ravvy.gymtrack.repository.InstituicaoRepository;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstituicaoService {

    private InstituicaoRepository instituicaoRepository;
    private AlunoRepository alunoRepository;
    private ProfessorRepository professorRepository;

    public InstituicaoService(InstituicaoRepository instituicaoRepository,
                              AlunoRepository alunoRepository,
                              ProfessorRepository professorRepository) {
        this.instituicaoRepository = instituicaoRepository;
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
    }

    public void save(Instituicao instituicao) {

        instituicaoRepository.findById(instituicao.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Instituição não encontrado no id " + instituicao.getId()));

        instituicaoRepository.save(instituicao);
    }

    public void delete(Instituicao instituicao) {

        instituicaoRepository.findById(instituicao.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Instituição não encontrado no id " + instituicao.getId()));

        instituicaoRepository.delete(instituicao);
    }

    public Optional<Instituicao> findById(Long id) {
        return instituicaoRepository.findById(id);
    }

    public void adicionarAlunoEmInstituicao(Long idAluno, Long idInstituicao) {

        Aluno aluno = alunoRepository.findById(idAluno)
                        .orElseThrow(() ->
                        new EntityNotFoundException("Aluno não encontrado no id " + idAluno));

        Instituicao instituicao = instituicaoRepository.findById(idInstituicao)
                .orElseThrow(() ->
                        new EntityNotFoundException("Instituição não encontrado no id " + idInstituicao));

        instituicao.getAlunos().add(aluno);
        instituicaoRepository.save(instituicao);
    }

    public void adicionarProfessorEmInstituicao(Long idProfessor, Long idInstituicao) {

        Professor professor = professorRepository.findById(idProfessor)
                .orElseThrow(() ->
                        new EntityNotFoundException("professor não encontrado no id " + idProfessor));

        Instituicao instituicao = instituicaoRepository.findById(idProfessor)
                .orElseThrow(() ->
                        new EntityNotFoundException("Instituição não encontrado no id " + idInstituicao));

        instituicao.getProfessores().add(professor);
        instituicaoRepository.save(instituicao);
    }

    public List<Aluno> listarTodosAlunosInstituicao() {
        return instituicaoRepository.findAllTodosAlunos();
    }

    public List<Professor> listarTodosProfessoresInstituicao() {
        return instituicaoRepository.findAllTodosProfessores();
    }

    public List<Instituicao> listarTodosInstituicao() {
        return instituicaoRepository.findAll();
    }

}