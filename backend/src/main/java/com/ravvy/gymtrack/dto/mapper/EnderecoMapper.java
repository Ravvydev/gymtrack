package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.dto.EnderecoResponse;
import com.ravvy.gymtrack.model.Endereco;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    EnderecoResponse toResponse(Endereco endereco);

    @Mapping(target = "id", ignore = true)
    Endereco toEntity(EnderecoCreateRequest request);
}
