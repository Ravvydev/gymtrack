package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.repository.EnderecoRepository;
import com.ravvy.gymtrack.util.Endereco;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoService(
            EnderecoRepository enderecoRepository
    ) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco save(
            EnderecoCreateRequest request
    ) {

        Endereco endereco = new Endereco();

        endereco.setRua(request.rua());
        endereco.setBairro(request.bairro());
        endereco.setComplemento(request.complemento());
        endereco.setCidade(request.cidade());
        endereco.setEstado(request.estado());
        endereco.setNumeroCasa(request.numeroCasa());
        endereco.setCep(request.cep());
        endereco.setUf(request.uf());

        return enderecoRepository.save(endereco);
    }

    public Endereco buscarPorId(Long id) {

        return enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Endereço não encontrado no id " + id
                        )
                );
    }
}