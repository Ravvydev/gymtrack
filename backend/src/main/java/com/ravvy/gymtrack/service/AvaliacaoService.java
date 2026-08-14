package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.AvaliacaoCreatedRequest;
import com.ravvy.gymtrack.dto.AvaliacaoResponse;
import com.ravvy.gymtrack.dto.AvaliacaoUploadRequest;
import com.ravvy.gymtrack.dto.mapper.AvaliacaoMapper;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Avaliacao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.repository.AvaliacaoRepository;
import com.ravvy.gymtrack.util.TipoClassificacao;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
@Getter
@Setter
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClassificacaoImcService classificacaoImcService;
    private final ClassificacaoRceService classificacaoRceService;
    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ClassificacaoImcService classificacaoImcService, ClassificacaoRceService classificacaoRceService, AlunoService alunoService, ProfessorService professorService, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.classificacaoImcService = classificacaoImcService;
        this.classificacaoRceService = classificacaoRceService;
        this.alunoService = alunoService;
        this.professorService = professorService;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @Transactional
    public AvaliacaoResponse criar(AvaliacaoCreatedRequest request) {

        Aluno aluno = alunoService.buscarPorId(request.alunoId());
        Professor professor = professorService.buscarPorId(request.professorId());

        Double imc = calcularIMC(
                request.peso(),
                request.altura()
        );
        Double rce = calcularRCE(
                request.perimetroCintura(),
                request.altura()
        );

        TipoClassificacao classificacaoImc =
                calcularClassificacaoImc(aluno, imc);

        TipoClassificacao classificacaoRce =
                classificacaoRceService.classificar(rce);

        Avaliacao avaliacao = avaliacaoMapper
                .toEntity(
                        request,
                        aluno,
                        professor,
                        imc,
                        rce,
                        classificacaoImc,
                        classificacaoRce
                );

        Avaliacao avaliacaoSalva = avaliacaoRepository.save(avaliacao);

        return avaliacaoMapper.toResponse(avaliacaoSalva);
    }

    @Transactional
    public void deleteById(Long id) {

        Avaliacao avaliacao = buscarPorId(id);
        avaliacaoRepository.delete(avaliacao);
    }

    @Transactional(readOnly = true)
    public Avaliacao buscarPorId(Long id) {
        return avaliacaoRepository.findById(id)
                .orElseThrow(()
                        -> new EntityNotFoundException("Avaliação não encontrada no id " + id));

    }

    @Transactional
    public AvaliacaoResponse upload(
            AvaliacaoUploadRequest request,
            Long id
    ) {

        Avaliacao avaliacao = buscarPorId(id);

        Aluno aluno = alunoService.buscarPorId(
                request.alunoId()
        );

        Professor professor = professorService.buscarPorId(
                request.professorId()
        );

        Double imc = calcularIMC(
                request.peso(),
                request.altura()
        );

        Double rce = calcularRCE(
                request.perimetroCintura(),
                request.altura()
        );

        TipoClassificacao classficacaoImc =
                calcularClassificacaoImc(
                        aluno,
                        imc
                );

        TipoClassificacao classificacaoRce =
                classificacaoRceService.classificar(rce);

        avaliacaoMapper.uploadAvaliacao(
                request,
                avaliacao,
                aluno,
                professor,
                imc,
                rce,
                classficacaoImc,
                classificacaoRce
        );

        Avaliacao avaliacaoSalva =
                avaliacaoRepository.save(avaliacao);

        return avaliacaoMapper.toResponse(
                avaliacaoSalva
        );
    }

    @Transactional
    public AvaliacaoResponse toResponse(Avaliacao avaliacao) {
        return avaliacaoMapper.toResponse(avaliacao);
    }

    public List<AvaliacaoResponse> buscarAvaliacoesPorData(
            Long professorId,
            LocalDate dataAvaliacao) {

        if (dataAvaliacao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data informada deve estar no passado!"
            );
        }

        List<Avaliacao> avaliacoes =
                avaliacaoRepository.findByProfessorIdAndDataAvaliacao(
                        professorId,
                        dataAvaliacao
                );

        return avaliacoes.stream()
                .map(avaliacaoMapper::toResponse)
                .toList();
    }

    private TipoClassificacao calcularClassificacaoImc(
            Aluno aluno,
            Double imc
    ) {
        return classificacaoImcService.classificar(
                aluno.getSexo(),
                YearOldService.calcularIdade(
                        aluno.getDataNascimento()
                ),
                imc
        );
    }

    @Transactional(readOnly = true)
    public AvaliacaoResponse buscarResponsePorId(Long id) {

        Avaliacao avaliacao = avaliacaoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Avaliação não encontrada no id " + id
                        )
                );

        return avaliacaoMapper.toResponse(avaliacao);
    }

    private Double calcularIMC(Double peso, Double altura) {

        double imc = peso / (altura * altura);

        return BigDecimal.valueOf(imc)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    private Double calcularRCE(Double perimetroCintura, Double altura) {

        double rce = perimetroCintura / (altura * 100);

        return BigDecimal.valueOf(rce)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }
}