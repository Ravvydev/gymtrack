package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.AlunoResponseDTO;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.repository.AlunoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    // Dependencia
    private AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public void save(Aluno aluno) {

        alunoRepository.findById(aluno.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Instituicao não encontrado no id " + aluno.getId()));


        alunoRepository.save(aluno);
    }

    public void delete(Aluno aluno) {

        alunoRepository.findById(aluno.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Instituicao não encontrado no id " + aluno.getId()));


        alunoRepository.delete(aluno);
    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado no id "+ id));
    }

    public Double calcularIMC(Avaliacao avaliacao) {
        return avaliacao.getPeso() / (avaliacao.getAltura() * avaliacao.getAltura());
    }

    public Double calcularRCE(Avaliacao avaliacao) {
        return avaliacao.getPerimetroCintura() / avaliacao.getAltura();
    }

}
