package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.repository.AlunoRepository;
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

        if (alunoRepository.existsById(aluno.getId()))
            throw new RuntimeException("Aluno já existente");

        alunoRepository.save(aluno);
    }

    public void delete(Aluno aluno) {

        if (!alunoRepository.existsById(aluno.getId()))
            throw new RuntimeException("O aluno não existe");

        alunoRepository.delete(aluno);
    }

    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }

    public Double calcularIMC(Avaliacao avaliacao) {
        return avaliacao.getPeso() / (avaliacao.getAltura() * avaliacao.getAltura());
    }

    public Double calcularRCE(Avaliacao avaliacao) {
        return avaliacao.getPerimetroCintura() / avaliacao.getAltura();
    }

}
