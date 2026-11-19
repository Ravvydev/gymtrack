package com.ravvy.gymtrack.aluno.service;

import com.ravvy.gymtrack.aluno.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.aluno.dto.AlunoResponse;
import com.ravvy.gymtrack.aluno.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.aluno.mapper.AlunoMapper;
import com.ravvy.gymtrack.aluno.repository.AlunoRepository;
import com.ravvy.gymtrack.endereco.dto.EnderecoVinculoValidator;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.endereco.service.EnderecoService;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.instituicao.repository.InstituicaoRepository;
import com.ravvy.gymtrack.instituicao.service.InstituicaoService;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.professor.repository.ProfessorRepository;
import com.ravvy.gymtrack.professor.service.ProfessorService;
import com.ravvy.gymtrack.shared.exception.EmailDuplicadoException;
import com.ravvy.gymtrack.shared.exception.SenhaAtualIncorretaException;
import com.ravvy.gymtrack.shared.senhas.dto.UpdateSenhaRequest;
import com.ravvy.gymtrack.shared.senhas.validation.SenhaValidator;
import com.ravvy.gymtrack.shared.util.Cpf;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.usuario.entity.Usuario;
import com.ravvy.gymtrack.usuario.enums.PapelUsuario;
import com.ravvy.gymtrack.usuario.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlunoService {

    // Dependencia
    private final PasswordEncoder passwordEncoder;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final ProfessorService professorService;
    private final InstituicaoService instituicaoService;
    private final AlunoMapper alunoMapper;
    private final UsuarioRepository usuarioRepository;
    private final ProfessorRepository professorRepository;
    private final InstituicaoRepository instituicaoRepository;
    private final EnderecoVinculoValidator enderecoVinculoValidator;

    public AlunoService(PasswordEncoder passwordEncoder, AlunoRepository alunoRepository, EnderecoService enderecoService, ProfessorService professorService, InstituicaoService instituicaoService, AlunoMapper alunoMapper, UsuarioRepository usuarioRepository, ProfessorRepository professorRepository, InstituicaoRepository instituicaoRepository, EnderecoVinculoValidator enderecoVinculoValidator) {
        this.passwordEncoder = passwordEncoder;
        this.alunoRepository = alunoRepository;
        this.enderecoService = enderecoService;
        this.professorService = professorService;
        this.instituicaoService = instituicaoService;
        this.alunoMapper = alunoMapper;
        this.usuarioRepository = usuarioRepository;
        this.professorRepository = professorRepository;
        this.instituicaoRepository = instituicaoRepository;
        this.enderecoVinculoValidator = enderecoVinculoValidator;
    }

    @Transactional
    public AlunoResponse save(AlunoCreateRequest request) {

        enderecoVinculoValidator.garantirDisponivel(request.enderecoId());

        SenhaValidator.validar(request.senha());
        String senhaHash = passwordEncoder.encode(request.senha());

        Email email = new Email(
                request.email()
        );

        if (usuarioRepository.existsByEmailEndereco(email)) {
            throw new EmailDuplicadoException();
        }

        Usuario usuario = new Usuario(
                email,
                senhaHash,
                PapelUsuario.ALUNO
        );

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Professor professor = professorService.buscarPorId(
                request.professorId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        Cpf cpf = new Cpf(request.cpf());

        Aluno aluno = alunoMapper.toEntity(
                request,
                endereco,
                professor,
                instituicao,
                cpf,
                usuario
        );

        Aluno alunoSalvo = alunoRepository.save(aluno);

        return alunoMapper.toResponse(alunoSalvo);
    }


    @Transactional
    public void update(AlunoUpdateRequest request, Long id) {

        Aluno aluno = buscarPorId(id);

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Professor professor = professorService.buscarPorId(
                request.professorId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        alunoMapper.updateEntity(
                aluno,
                request,
                endereco,
                professor,
                instituicao
        );

        alunoMapper.toResponse(aluno);
    }

    @Transactional
    public void updateSenha(UpdateSenhaRequest request, Long idAluno) {

        Aluno aluno = buscarPorId(idAluno);

        if (!passwordEncoder.matches(
                request.senhaAtual(),
                aluno.getUsuario().getSenhaHash())) {

            throw new SenhaAtualIncorretaException();
        }

        SenhaValidator.validar(request.senhaNova());

        String hashNovo = passwordEncoder.encode(request.senhaNova());

        aluno.getUsuario().alterarSenha(hashNovo);

        alunoRepository.save(aluno);
    }

    @Transactional
    public AlunoResponse buscarResponsePorId(Long id) {

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Aluno não encontrado no id " + id
                        )
                );

        return alunoMapper.toResponse(aluno);
    }

    public void deleteById(Long id) {

        Aluno aluno = buscarPorId(id);
        alunoRepository.delete(aluno);

    }

    public Aluno buscarPorId(Long id) {
        return alunoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Aluno não encontrado no id " + id));
    }

    // ===== AlunoService: métodos (precisa injetar ProfessorRepository e InstituicaoRepository) =====

    @Transactional
    public void vincularProfessor(Long idAluno, Long idProfessor) {
        Aluno aluno = buscarPorId(idAluno);
        Professor professor = professorRepository.findById(idProfessor).orElseThrow(
                () -> new EntityNotFoundException("Professor não encontrado no id " + idProfessor));

        if (aluno.getProfessor() != null) {
            // Use sua exceção de conflito (409) em vez de IllegalArgumentException
            throw new IllegalArgumentException("O aluno já possui professor. Desvincule antes de trocar.");
        }
        aluno.setProfessor(professor);
    }

    @Transactional
    public void desvincularProfessor(Long idAluno) {
        buscarPorId(idAluno).setProfessor(null);
    }

    @Transactional
    public void vincularInstituicao(Long idAluno, Long idInstituicao) {
        Aluno aluno = buscarPorId(idAluno);
        Instituicao instituicao = instituicaoRepository.findById(idInstituicao).orElseThrow(
                () -> new EntityNotFoundException("Instituição não encontrada no id " + idInstituicao));

        if (aluno.getInstituicao() != null) {
            throw new IllegalArgumentException("O aluno já possui instituição. Desvincule antes de trocar.");
        }
        aluno.setInstituicao(instituicao);
    }

    @Transactional
    public void desvincularInstituicao(Long idAluno) {
        buscarPorId(idAluno).setInstituicao(null);
    }

}
