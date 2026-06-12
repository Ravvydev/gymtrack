package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.AvaliacaoCreatedRequest;
import com.ravvy.gymtrack.dto.AvaliacaoResponse;
import com.ravvy.gymtrack.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.YearOldService;
import com.ravvy.gymtrack.util.TipoClassificacao;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component
public class AvaliacaoMapper {

    public Avaliacao toEntity(AvaliacaoCreatedRequest request,
                              Avaliacao avaliacao,
                              Aluno aluno,
                              Professor professor,
                              Double imc,
                              Double rce,
                              TipoClassificacao classificacao) {

        avaliacao.setAluno(aluno);
        avaliacao.setProfessor(professor);
        avaliacao.setPeso(request.peso());
        avaliacao.setAltura(request.altura());
        avaliacao.setPerimetroCintura(request.perimetroCintura());
        avaliacao.setEnvergadura(request.envergadura());
        avaliacao.setSexo(aluno.getSexo());
        avaliacao.setIdade(
                YearOldService.calcularIdade(aluno.getDataNascimento())
        );
        avaliacao.setImc(imc);
        avaliacao.setRce(rce);
        avaliacao.setDataAvaliacao(LocalDate.now());
        avaliacao.setZona(classificacao);

        return avaliacao;
    }

    public AvaliacaoResponse toResponse(Avaliacao avaliacao) {
        return new AvaliacaoResponse(
                avaliacao.getAluno().getNome(),
                avaliacao.getIdade(),
                avaliacao.getSexo(),
                avaliacao.getZona(),
                avaliacao.getDataAvaliacao(),
                avaliacao.getPeso(),
                avaliacao.getAltura(),
                avaliacao.getEnvergadura(),
                avaliacao.getPerimetroCintura(),
                avaliacao.getImc(),
                avaliacao.getRce(),
                avaliacao.getProfessor().getNome()
        );
    }


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
