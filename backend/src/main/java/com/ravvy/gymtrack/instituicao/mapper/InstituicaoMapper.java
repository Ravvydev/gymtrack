package com.ravvy.gymtrack.instituicao.mapper;

import com.ravvy.gymtrack.instituicao.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.instituicao.dto.InstituicaoResponse;
import com.ravvy.gymtrack.instituicao.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import com.ravvy.gymtrack.instituicao.entity.Instituicao;
import com.ravvy.gymtrack.shared.util.Email;
import com.ravvy.gymtrack.shared.util.Telefone;
import com.ravvy.gymtrack.usuario.entity.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(
        componentModel = "spring"
)
public interface InstituicaoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "professores", ignore = true)

    @Mapping(source = "request.nome", target = "nome")

    @Mapping(source = "endereco", target = "endereco")
    @Mapping(source = "usuario", target = "usuario")

    @Mapping(
            source = "request.telefone",
            target = "telefone",
            qualifiedByName = "toTelefone"
    )
    Instituicao toEntity(
            InstituicaoCreateRequest request,
            Endereco endereco,
            Usuario usuario
    );

    @Mapping(source = "instituicao.usuario.email", target = "email")
    @Mapping(source = "telefone.numero", target = "telefone")
    @Mapping(source = "endereco.localizacao", target = "endereco")
    @Mapping(source = "usuario.papel", target = "papel")
    InstituicaoResponse toResponse(Instituicao instituicao);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "alunos", ignore = true)
    @Mapping(target = "professores", ignore = true)
    @Mapping(target = "usuario", ignore = true)

    @Mapping(source = "endereco", target = "endereco")

    @Mapping(
            source = "request.telefone",
            target = "telefone",
            qualifiedByName = "toTelefone"
    )
    void updateEntity(InstituicaoUpdateRequest request,
                      @MappingTarget Instituicao instituicao,
                      Endereco endereco);

    @Named("toTelefone")
    default Telefone toTelefone(String numero) {
        return new Telefone(numero);
    }

    default String emailToResponse(Email email) {
        return email.toString();
    }
}
