package com.ravvy.gymtrack.repository;

import com.ravvy.gymtrack.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao,Long> {

    List<Avaliacao> findByProfessorIdAndDataAvaliacao(
            Long professorId,
            LocalDate dataAvaliacao
    );

}
