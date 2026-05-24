package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.InstituicaoRequestDTO;
import com.ravvy.gymtrack.dto.InstituicaoResponseDTO;
import com.ravvy.gymtrack.model.Instituicao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituicaoMapper {

    public Instituicao requestToEntity(InstituicaoRequestDTO requestDTO) {
        Instituicao instituicao = new Instituicao();
        instituicao.setId(requestDTO.getId());
        instituicao.setNome(requestDTO.getNome());
        instituicao.setEmail(requestDTO.getEmail());
        instituicao.setTelefone(requestDTO.getTelefone());
        instituicao.setEndereco(requestDTO.getEndereco());
        return instituicao;
    }

    public InstituicaoResponseDTO entityToResponseDTO(Instituicao entity) {
        InstituicaoResponseDTO ResponseDTO = new InstituicaoResponseDTO();
        entity.setNome(entity.getNome());
        entity.setEmail(entity.getEmail());
        entity.setTelefone(entity.getTelefone());
        entity.setEndereco(entity.getEndereco());
        return ResponseDTO;
    }

}
