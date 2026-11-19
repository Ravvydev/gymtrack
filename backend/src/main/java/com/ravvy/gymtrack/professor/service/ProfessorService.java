package com.ravvy.gymtrack.professor.service;

import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.aluno.repository.AlunoRepository;
import com.ravvy.gymtrack.endereco.dto.EnderecoVinculoValidator;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.endereco.service.EnderecoService;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.instituicao.service.InstituicaoService;
import com.ravvy.gymtrack.professor.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.professor.dto.ProfessorResponse;
import com.ravvy.gymtrack.professor.dto.ProfessorUpdateRequest;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.professor.mapper.ProfessorMapper;
import com.ravvy.gymtrack.professor.repository.ProfessorRepository;
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
import jakarta.transaction.Transactional;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Getter
public class ProfessorService {

    private final PasswordEncoder passwordEncoder;
    private final ProfessorRepository professorRepository;
    private final AlunoRepository alunoRepository;
    private final EnderecoService enderecoService;
    private final InstituicaoService instituicaoService;
    private final ProfessorMapper professorMapper;
    private final UsuarioRepository usuarioRepository;
    private final EnderecoVinculoValidator enderecoVinculoValidator;

    public ProfessorService(PasswordEncoder passwordEncoder, ProfessorRepository professorRepository, AlunoRepository alunoRepository, EnderecoService enderecoService, InstituicaoService instituicaoService, ProfessorMapper professorMapper, UsuarioRepository usuarioRepository, EnderecoVinculoValidator enderecoVinculoValidator) {
        this.passwordEncoder = passwordEncoder;
        this.professorRepository = professorRepository;
        this.alunoRepository = alunoRepository;
        this.enderecoService = enderecoService;
        this.instituicaoService = instituicaoService;
        this.professorMapper = professorMapper;
        this.usuarioRepository = usuarioRepository;
        this.enderecoVinculoValidator = enderecoVinculoValidator;
    }

    public ProfessorResponse save(ProfessorCreateRequest request) {

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
                PapelUsuario.PROFESSOR
        );

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        Cpf cpf = new Cpf(request.cpf());

        Professor professor = professorMapper.toEntity(
                request,
                endereco,
                instituicao,
                cpf,
                usuario
        );
        ;

        Professor professorSalvo =
                professorRepository.save(professor);

        return professorMapper.toResponse(professorSalvo);

    }

    public void deleteById(Long id) {
        Professor professor = buscarPorId(id);
        professorRepository.delete(professor);
    }

    public Professor buscarPorId(Long id) {
        return professorRepository.findById(id).
                orElseThrow(() -> new EntityNotFoundException("Professor não encontrado no id " + id));
    }

    @Transactional
    public ProfessorResponse update(ProfessorUpdateRequest request,
                                    Long id) {

        Professor professor = buscarPorId(id);

        Endereco endereco = enderecoService.buscarPorId(
                request.enderecoId());

        Instituicao instituicao = instituicaoService.buscarPorId(
                request.instituicaoId());

        professorMapper.updateEntity(
                request,
                professor,
                endereco,
                instituicao
        );

        professorRepository.save(professor);

        return professorMapper.toResponse(professor);
    }

    @Transactional
    public void updateSenha(UpdateSenhaRequest request,
                            Long id) {
        Professor professor = buscarPorId(id);

        if (!passwordEncoder.matches(
                request.senhaAtual(),
                professor.getUsuario().getSenhaHash())) {

            throw new SenhaAtualIncorretaException();
        }

        SenhaValidator.validar(request.senhaNova());

        String hashNovo = passwordEncoder.encode(request.senhaNova());

        professor.getUsuario().alterarSenha(hashNovo);

        professorRepository.save(professor);
    }

    @Transactional
    public void vincularAluno(Long idProfessor,
                              Long idAluno) {

        Professor professor = buscarPorId(idProfessor);
        Aluno aluno = alunoRepository.findById(idAluno).orElseThrow(
                () -> new EntityNotFoundException("Aluno não encontrado no id " + idAluno)
        );

        if (aluno.getProfessor() != null) {
            throw new IllegalArgumentException(
                    "O aluno já está vinculado ao professor"
            );
        }

        professor.getAlunos().add(aluno);
        aluno.setProfessor(professor);

    }

    @Transactional
    public ProfessorResponse buscarResponsePorId(Long id) {

        Professor professor = professorRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Professor não encontrado no id " + id
                        )
                );

        return professorMapper.toResponse(professor);
    }

}