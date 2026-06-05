package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.InstituicaoCreateRequest;
import com.ravvy.gymtrack.dto.InstituicaoResponse;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.service.EnderecoService;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import org.springframework.stereotype.Component;

@Component
public class InstituicaoMapper {

    private final EnderecoService enderecoService;

    public InstituicaoMapper(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    public Instituicao toEntity(InstituicaoCreateRequest requestDTO) {
        Instituicao instituicao = new Instituicao();
        instituicao.setNome(requestDTO.nome());
        instituicao.setEmail(new Email(requestDTO.email(), requestDTO.senha()));
        instituicao.setTelefone(new Telefone(requestDTO.telefone()));
        instituicao.setEndereco(enderecoService.buscarPorId(requestDTO.enderecoId()));
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

}
