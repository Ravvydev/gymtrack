package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.dto.InstituicaoUpdateRequest;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.model.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import org.springframework.stereotype.Component;

@Component
public class InstituicaoMapper {

    public Instituicao toEntity(InstituicaoCreateRequest requestDTO, Endereco endereco) {

        Instituicao instituicao = new Instituicao();
        instituicao.setNome(requestDTO.nome());
        instituicao.setEmail(new Email(requestDTO.email(), requestDTO.senha()));
        instituicao.setTelefone(new Telefone(requestDTO.telefone()));

        instituicao.setEndereco(endereco);
        return instituicao;

    }

    public InstituicaoResponse toResponse(Instituicao entity) {
        return new InstituicaoResponse(
                entity.getId(),
                entity.getNome(),
                entity.getEmail().getEmail(),
                entity.getTelefone().getNumero(),
                entity.getEndereco().getLocalizacao()
        );
    }

    public void updateEntity(InstituicaoUpdateRequest request,
                             Instituicao instituicao,
                             Endereco endereco) {

        instituicao.setNome(request.nome());
        instituicao.setTelefone(new Telefone(
                request.telefone()
        ));

        instituicao.setEndereco(endereco);

    }

}
