package com.ravvy.gymtrack.professor.mapper;

import com.ravvy.gymtrack.professor.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.professor.dto.ProfessorResponse;
import com.ravvy.gymtrack.professor.dto.ProfessorUpdateRequest;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.professor.entity.Professor;
import com.ravvy.gymtrack.shared.validacao.YearOldService;
import com.ravvy.gymtrack.shared.util.Cpf;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.shared.util.Telefone;
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
    @Mapping(source = "cpf", target = "cpf")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(
            target = "email",
            expression = "java(criarEmail(request.email()))")
    @Mapping(target = "senhaHash", ignore = true)
    Professor toEntity(ProfessorCreateRequest request,
                       Endereco endereco,
                       Instituicao instituicao,
                       Cpf cpf);

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
    @Mapping(target = "senhaHash", ignore = true)
    void updateEntity(ProfessorUpdateRequest request,
                      @MappingTarget Professor professor,
                      Endereco endereco,
                      Instituicao instituicao);

    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

    default Email criarEmail(String email) {
        return new Email(email);
    }

    default Cpf toCpf(String numero) {
        return new Cpf(numero);
    }

}