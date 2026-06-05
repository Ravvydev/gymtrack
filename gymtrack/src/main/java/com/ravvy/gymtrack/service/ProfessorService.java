package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.ProfessorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@Getter
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final AlunoService alunoService;
    private final AvaliacaoService avaliacaoService;

    public ProfessorService(ProfessorRepository professorRepository, AlunoService alunoService, AvaliacaoService avaliacaoService) {
        this.professorRepository = professorRepository;
        this.alunoService = alunoService;
        this.avaliacaoService = avaliacaoService;
    }

    public void save(Professor professor) {

        if (professor == null) {
            throw new IllegalArgumentException("Professor não pode ser nulo");
        }

        professorRepository.save(professor);
    }

    public void deleteById(Long id) {
        Professor professor = buscarPorId(id);
        professorRepository.delete(professor);
    }

    public Optional<Professor> findById(long id) {
        return professorRepository.findById(id);
    }

    public Avaliacao registrarAvaliacao(Long idAluno, Double peso, Double altura, Double perimetroCintura) {

        Aluno aluno = alunoService.buscarPorId(idAluno);

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAluno(aluno);
        avaliacao.setPeso(peso);
        avaliacao.setAltura(altura);
        avaliacao.setPerimetroCintura(perimetroCintura);
        avaliacaoService.salvar(avaliacao);

        return avaliacao;
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Professor não encontrado no id " + id));
    }


}