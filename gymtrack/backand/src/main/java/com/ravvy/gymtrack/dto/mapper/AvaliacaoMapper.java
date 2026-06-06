package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.YearOldService;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoMapper {

    public void uploadAvaliacao(AvaliacaoUploadRequest request,
                                Avaliacao avaliacao,
                                Aluno aluno,
                                Professor professor) {

        avaliacao.setAluno(aluno);

        avaliacao.setIdade(
                YearOldService.calcularIdade(request.dataNascimentoAluno())
        );

        avaliacao.setProfessor(professor);

        avaliacao.setSexo(request.sexo());

        avaliacao.setPeso(request.peso());

        avaliacao.setAltura(request.altura());

        avaliacao.setEnvergadura(request.envergadura());

        avaliacao.setPerimetroCintura(request.perimetroCintura());

    }

}
