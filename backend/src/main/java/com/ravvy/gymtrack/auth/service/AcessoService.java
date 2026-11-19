package com.ravvy.gymtrack.auth.service;

import com.ravvy.gymtrack.aluno.repository.AlunoRepository;
import com.ravvy.gymtrack.instituicao.repository.InstituicaoRepository;
import com.ravvy.gymtrack.professor.repository.ProfessorRepository;
import com.ravvy.gymtrack.security.autenticacao.UsuarioAutenticado;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
/**
 * Regras de "esse recurso é do usuário logado?" usadas nos @PreAuthorize.
 * Uso: @PreAuthorize("hasRole('ADMIN') or @acesso.donoDoAluno(#id)")

 * Todas as checagens comparam o id do USUÁRIO logado (vem do token/banco) com o
 * usuário dono do recurso, direto no banco, sem carregar entidades.
 * Se não houver usuário autenticado, tudo retorna false (nega por padrão).
 */
@Component("acesso")
@Transactional(readOnly = true)
public class AcessoService {

    private final AlunoRepository alunoRepository;
    private final ProfessorRepository professorRepository;
    private final InstituicaoRepository instituicaoRepository;

    public AcessoService(AlunoRepository alunoRepository,
                         ProfessorRepository professorRepository,
                         InstituicaoRepository instituicaoRepository) {
        this.alunoRepository = alunoRepository;
        this.professorRepository = professorRepository;
        this.instituicaoRepository = instituicaoRepository;
    }

    // ---------- donos (o próprio) ----------

    public boolean donoDoAluno(Long alunoId) {
        Long usuarioId = usuarioLogadoId();
        return usuarioId != null && alunoId != null
                && alunoRepository.existsByIdAndUsuarioId(alunoId, usuarioId);
    }

    public boolean donoDoProfessor(Long professorId) {
        Long usuarioId = usuarioLogadoId();
        return usuarioId != null && professorId != null
                && professorRepository.existsByIdAndUsuarioId(professorId, usuarioId);
    }

    public boolean donoDaInstituicao(Long instituicaoId) {
        Long usuarioId = usuarioLogadoId();
        return usuarioId != null && instituicaoId != null
                && instituicaoRepository.existsByIdAndUsuarioId(instituicaoId, usuarioId);
    }

    // ---------- leitura de dados de outra pessoa (depende de vínculo) ----------

    /** O próprio aluno, o professor dele ou a instituição dele. */
    public boolean podeVerAluno(Long alunoId) {
        Long usuarioId = usuarioLogadoId();
        if (usuarioId == null || alunoId == null) {
            return false;
        }
        return alunoRepository.existsByIdAndUsuarioId(alunoId, usuarioId)
                || alunoRepository.existsByIdAndProfessorUsuarioId(alunoId, usuarioId)
                || alunoRepository.existsByIdAndInstituicaoUsuarioId(alunoId, usuarioId);
    }

    /** O próprio professor, a instituição dele ou um aluno vinculado a ele. */
    public boolean podeVerProfessor(Long professorId) {
        Long usuarioId = usuarioLogadoId();
        if (usuarioId == null || professorId == null) {
            return false;
        }
        return professorRepository.existsByIdAndUsuarioId(professorId, usuarioId)
                || professorRepository.existsByIdAndInstituicaoUsuarioId(professorId, usuarioId)
                || professorRepository.existsByIdAndAlunosUsuarioId(professorId, usuarioId);
    }

    // ---------- util ----------

    private Long usuarioLogadoId() {
        Authentication autenticacao = SecurityContextHolder.getContext().getAuthentication();
        if (autenticacao != null && autenticacao.getPrincipal() instanceof UsuarioAutenticado usuario) {
            return usuario.getId();
        }
        return null;
    }
}