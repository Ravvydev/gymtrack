package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.repository.AlunoRepository;
import com.ravvy.gymtrack.repository.InstituicaoRepository;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void saveInstituicao(Instituicao instituicao) {

        if (instituicaoRepository.existsById(instituicao.getId())) {
            throw new RuntimeException("A instituição já existe");
        }

        instituicaoRepository.save(instituicao);
    }

    public void deleteInstituicao(Instituicao instituicao) {
        instituicaoRepository.delete(instituicao);
    }

    public List<Instituicao> findAllInstituicao() {
        return instituicaoRepository.findAll();
    }

    public void adicionarAlunoEmInstituicao(Long id) {
        if (!alunoRepository.existsById(id))
            throw new RuntimeException("Id do professor invalido");
        instituicaoRepository.adicionarAlunoEmInstituicao(id);
    }

    public void adicionarProfessorEmInstituicao(Long id) {
        if (!professorRepository.existsById(id))
            throw new RuntimeException("Id do professor invalido");
        instituicaoRepository.adicionarAlunoEmInstituicao(id);
    }
}