package com.ravvy.gymtrack.endereco.dto;

public record EnderecoResponse(
        Long id,
        String rua,
        String bairro,
        String complemento,
        String cidade,
        String estado,
        Integer numeroCasa,
        String cep,
        String uf
) {
}
