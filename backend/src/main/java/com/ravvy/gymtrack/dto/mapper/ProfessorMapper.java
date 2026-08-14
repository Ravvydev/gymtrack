package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.dto.ProfessorResponse;
import com.ravvy.gymtrack.dto.ProfessorUpdateRequest;
import com.ravvy.gymtrack.model.Endereco;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.YearOldService;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring",
        imports = YearOldService.class
)
public interface ProfessorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "instituicao", target = "instituicao")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(target = "email",
            expression = "java(criarEmail(request.email(), request.senha()))")
    Professor toEntity(ProfessorCreateRequest request,
                       Endereco endereco,
                       Instituicao instituicao);

    @Mapping(target = "idade",
            expression = "java(YearOldService.calcularIdade(professor.getDataNascimento()))")
    @Mapping(source = "instituicao.nome", target = "instituicaoNome")
    @Mapping(source = "email.endereco", target = "email")
    @Mapping(source = "endereco.localizacao", target = "endereco")
    @Mapping(source = "telefone.numero", target = "telefone")
    ProfessorResponse toResponse(Professor professor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cpf", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(source = "request.nome", target = "nome")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "instituicao", target = "instituicao")
    void updateEntity(ProfessorUpdateRequest request,
                      @MappingTarget Professor professor,
                      Endereco endereco,
                      Instituicao instituicao);

    default Email criarEmail(String endereco, String senha) {
        return new Email(endereco, senha);
    }

    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

}