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

import java.time.LocalDate;
import java.util.List;

@Service
@Getter
@Setter
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final ClassificacaoImcService  classificacaoImcService;
    private final AlunoService alunoService;
    private final ProfessorService professorService;
    private final AvaliacaoMapper avaliacaoMapper;

    public AvaliacaoService(AvaliacaoRepository avaliacaoRepository, ClassificacaoImcService classificacaoImcService, AlunoService alunoService, ProfessorService professorService, AvaliacaoMapper avaliacaoMapper) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.classificacaoImcService = classificacaoImcService;
        this.alunoService = alunoService;
        this.professorService = professorService;
        this.avaliacaoMapper = avaliacaoMapper;
    }

    @Transactional
    public AvaliacaoResponse criar(AvaliacaoCreatedRequest request) {

        Avaliacao avaliacao = new Avaliacao();
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

        TipoClassificacao classificacao =
                calcularTipoClassificacao(aluno , imc);

        avaliacaoMapper.toEntity(
                request,
                avaliacao,
                aluno,
                professor,
                imc,
                rce,
                classificacao
        );

        avaliacaoRepository.save(avaliacao);

        return avaliacaoMapper.toResponse(avaliacao);
    }

    @Transactional(readOnly = true)
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
    public Avaliacao upload(AvaliacaoUploadRequest request,
                            Long id) {

        Avaliacao avaliacao = buscarPorId(id);

        Aluno aluno = alunoService.buscarPorId(
                request.alunoId()
        );

        Professor professor = professorService.buscarPorId(
                request.professorId()
        );

        avaliacaoMapper.uploadAvaliacao(
                request,
                avaliacao,
                aluno,
                professor
        );

        avaliacao.setImc(
                calcularIMC(request.peso() , request.altura())
        );

        avaliacao.setRce(
                calcularRCE(request.perimetroCintura(), request.altura())
        );

        avaliacao.setZona(
                calcularTipoClassificacao(aluno,
                        calcularIMC(request.peso(), request.altura()))
        );

        return avaliacaoRepository.save(avaliacao);
    }

    @Transactional
    public AvaliacaoResponse toResponse(Avaliacao avaliacao) {
        return avaliacaoMapper.toResponse(avaliacao);
    }

    public List<AvaliacaoResponse> buscarAvaliacoesPorData(
            Long professorId,
            LocalDate dataCriacao) {

        if (dataCriacao.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "A data informada deve estar no passado!"
            );
        }

        return avaliacaoRepository.findByProfessorIdAndDataCriacao(
                professorId,
                dataCriacao
        );
    }

    private TipoClassificacao calcularTipoClassificacao(Aluno aluno, Double imc) {
        return classificacaoImcService.classificar(
                                aluno.getSexo(),
                                YearOldService.calcularIdade(aluno.getDataNascimento()),
                                imc
                        );
    }

    private Double calcularIMC(Double peso, Double altura) {
        return peso / (altura * altura);
    }

    private Double calcularRCE(Double perimetroCintura, Double altura) {
        return perimetroCintura / altura;
    }

}