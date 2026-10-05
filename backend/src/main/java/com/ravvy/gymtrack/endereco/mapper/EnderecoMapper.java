package com.ravvy.gymtrack.endereco.mapper;

import com.ravvy.gymtrack.endereco.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.endereco.dto.EnderecoResponse;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoResponse toResponse(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    Endereco toEntity(EnderecoCreateRequest request);
}
