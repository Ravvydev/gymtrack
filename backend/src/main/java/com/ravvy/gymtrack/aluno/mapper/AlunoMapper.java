package com.ravvy.gymtrack.aluno.mapper;

import com.ravvy.gymtrack.aluno.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.aluno.dto.AlunoResponse;
import com.ravvy.gymtrack.aluno.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.aluno.entity.Aluno;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.shared.util.Cpf;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.shared.util.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.sexo", target = "sexo")
    @Mapping(source = "request.dataNascimento", target = "dataNascimento")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "professor", target = "professor")
    @Mapping(source = "instituicao", target = "instituicao")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(
            target = "email",
            expression = "java(criarEmail(request.email()))"
    )
    @Mapping(target = "senhaHash", ignore = true)
    Aluno toEntity(AlunoCreateRequest request,
                   Endereco endereco,
                   Professor professor,
                   Instituicao instituicao,
                   Cpf cpf);

    @Mapping(source = "email.endereco", target = "email")
    @Mapping(source = "telefone.numero", target = "telefone")
    @Mapping(source = "endereco.localizacao", target = "endereco")
    @Mapping(source = "instituicao.nome", target = "instituicaoNome")
    AlunoResponse toResponse(Aluno aluno);

    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.sexo", target = "sexo")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(source = "request.dataNascimento", target = "dataNascimento")
    @Mapping(source = "professor", target = "professor")
    @Mapping(source = "instituicao", target = "instituicao")
    @Mapping(source = "endereco", target = "endereco")
    /// Atributos que o mapStruct pode ignorar
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "senhaHash", ignore = true)
    void updateEntity(
            @MappingTarget Aluno aluno,
            AlunoUpdateRequest request,
            Endereco endereco,
            Professor professor,
            Instituicao instituicao);

    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

    default Email criarEmail(String email) {
        return new Email(email);
    }

}