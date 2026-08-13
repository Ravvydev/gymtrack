package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.dto.EnderecoResponse;
import com.ravvy.gymtrack.model.Endereco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnderecoMapper {

    public EnderecoResponse toResponse(Endereco endereco) {

        return new EnderecoResponse(
                endereco.getId(),
                endereco.getRua(),
                endereco.getBairro(),
                endereco.getComplemento(),
                endereco.getCidade(),
                endereco.getEstado(),
                endereco.getNumeroCasa(),
                endereco.getCep(),
                endereco.getUf()
        );

    }

    public Endereco toEntity(EnderecoCreateRequest request) {
        Endereco endereco = new Endereco();

        endereco.setRua(request.rua());
        endereco.setBairro(request.bairro());
        endereco.setComplemento(request.complemento());
        endereco.setCidade(request.cidade());
        endereco.setEstado(request.estado());
        endereco.setNumeroCasa(request.numeroCasa());
        endereco.setCep(request.cep());
        endereco.setUf(request.uf());
        return endereco;
    }

}
