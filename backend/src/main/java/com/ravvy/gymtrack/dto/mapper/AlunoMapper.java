package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.dto.AlunoResponse;
import com.ravvy.gymtrack.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Endereco;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.sexo", target = "sexo")
    @Mapping(source = "request.cpf", target = "cpf")
    @Mapping(source = "request.dataNascimento", target = "dataNascimento")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "professor", target = "professor")
    @Mapping(source = "instituicao", target = "instituicao")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(
            target = "email",
            expression = "java(criarEmail(request.email(), request.senha()))"
    )
    Aluno toEntity(AlunoCreateRequest request,
                   Endereco endereco,
                   Professor professor,
                   Instituicao instituicao);


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
    void updateEntity(
            @MappingTarget Aluno aluno,
            AlunoUpdateRequest request,
            Endereco endereco,
            Professor professor,
            Instituicao instituicao);

    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

    default Email criarEmail(String email, String senha) {
        return new Email(email, senha);
    }

}