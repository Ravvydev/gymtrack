package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.model.Endereco;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        componentModel = "spring"
)
public interface InstituicaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "professores", ignore = true)
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "request.telefone", target = "telefone")
    @Mapping(
            target = "email",
            expression = "java(criarEmail(request.email(), request.senha()))"
    )
    Instituicao toEntity(
            InstituicaoCreateRequest request,
            Endereco endereco
    );

    @Mapping(source = "email.endereco", target = "email")
    @Mapping(source = "telefone.numero", target = "telefone")
    @Mapping(source = "endereco.localizacao", target = "endereco")
    InstituicaoResponse toResponse(Instituicao instituicao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "professores", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "request.telefone", target = "telefone")
    void updateEntity(InstituicaoUpdateRequest request,
                      @MappingTarget Instituicao instituicao,
                      Endereco endereco);

    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

    default Email criarEmail(String email, String senha) {
        return new Email(email, senha);
    }
}
