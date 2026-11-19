package com.ravvy.gymtrack.professor.mapper;

import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.professor.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.professor.dto.ProfessorResponse;
import com.ravvy.gymtrack.professor.dto.ProfessorUpdateRequest;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.shared.util.Cpf;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.shared.util.Telefone;
import com.ravvy.gymtrack.shared.validacao.YearOldService;
import com.ravvy.gymtrack.usuario.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring",
        imports = YearOldService.class
)
public interface ProfessorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)

    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.sexo", target = "sexo")
    @Mapping(source = "request.dataNascimento", target = "dataNascimento")

    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "instituicao", target = "instituicao")
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "usuario", target = "usuario")

    @Mapping(
            source = "request.telefone",
            target = "telefone",
            qualifiedByName = "toTelefone"
    )
    Professor toEntity(ProfessorCreateRequest request,
                       Endereco endereco,
                       Instituicao instituicao,
                       Cpf cpf,
                       Usuario usuario);

    @Mapping(target = "idade",
            expression = "java(YearOldService.calcularIdade(professor.getDataNascimento()))")
    @Mapping(source = "instituicao.nome", target = "instituicaoNome")
    @Mapping(source = "endereco.localizacao", target = "endereco")
    @Mapping(source = "telefone.numero", target = "telefone")
    @Mapping(source = "professor.usuario.email", target = "email")
    @Mapping(source = "usuario.papel", target = "papel")
    ProfessorResponse toResponse(Professor professor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "usuario", ignore = true)

    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.sexo", target = "sexo")
    @Mapping(source = "request.dataNascimento", target = "dataNascimento")

    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "instituicao", target = "instituicao")

    @Mapping(
            source = "request.telefone",
            target = "telefone",
            qualifiedByName = "toTelefone"
    )
    void updateEntity(ProfessorUpdateRequest request,
                      @MappingTarget Professor professor,
                      Endereco endereco,
                      Instituicao instituicao);

    @Named("toTelefone")
    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

    default String EmailtoString(Email email) {
        return email.toString();
    }

}