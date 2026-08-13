package com.ravvy.gymtrack.service;

import com.ravvy.gymtrack.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.dto.EnderecoResponse;
import com.ravvy.gymtrack.dto.mapper.EnderecoMapper;
import com.ravvy.gymtrack.repository.EnderecoRepository;
import com.ravvy.gymtrack.model.Endereco;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    public EnderecoService(
            EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper
    ) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    @Transactional
    public EnderecoResponse save(
            EnderecoCreateRequest request
    ) {

        Endereco endereco = enderecoMapper.toEntity(request);


        Endereco enderecoSalvo = enderecoRepository.save(endereco);

        return enderecoMapper.toResponse(enderecoSalvo);
    }

    @Transactional(readOnly = true)
    public Endereco buscarPorId(Long id) {

        return enderecoRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Endereço não encontrado no id " + id
                        )
                );
    }

    @Transactional(readOnly = true)
    public EnderecoResponse buscarResponsePorId(Long id) {
        Endereco endereco = buscarPorId(id);
        return enderecoMapper.toResponse(endereco);
    }
}