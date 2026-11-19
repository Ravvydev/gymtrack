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
import com.ravvy.gymtrack.usuario.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

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
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "usuario", target = "usuario")

    @Mapping(
            source = "request.telefone",
            target = "telefone",
            qualifiedByName = "toTelefone"
    )
    Aluno toEntity(AlunoCreateRequest request,
                   Endereco endereco,
                   Professor professor,
                   Instituicao instituicao,
                   Cpf cpf,
                   Usuario usuario);

    @Mapping(source = "telefone.numero", target = "telefone")
    @Mapping(source = "endereco.localizacao", target = "endereco")
    @Mapping(source = "instituicao.nome", target = "instituicaoNome")
    @Mapping(source = "aluno.usuario.email", target = "email")
    @Mapping(source = "usuario.papel", target = "papel")
    AlunoResponse toResponse(Aluno aluno);

    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.sexo", target = "sexo")
    @Mapping(source = "request.dataNascimento", target = "dataNascimento")
    @Mapping(source = "professor", target = "professor")
    @Mapping(source = "instituicao", target = "instituicao")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(
            source = "request.telefone",
            target = "telefone",
            qualifiedByName = "toTelefone"
    )
    /// Atributos que o mapStruct pode ignorar
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avaliacoes", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    void updateEntity(
            @MappingTarget Aluno aluno,
            AlunoUpdateRequest request,
            Endereco endereco,
            Professor professor,
            Instituicao instituicao);

    default String EmailtoString(Email emailClass) {
        return emailClass.toString();
    }

    @Named("toTelefone")
    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }
}