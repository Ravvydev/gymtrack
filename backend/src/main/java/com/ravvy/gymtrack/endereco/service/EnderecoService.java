package com.ravvy.gymtrack.endereco.service;

import com.ravvy.gymtrack.endereco.dto.EnderecoCreateRequest;
import com.ravvy.gymtrack.endereco.dto.EnderecoResponse;
import com.ravvy.gymtrack.endereco.dto.EnderecoVinculoValidator;
import com.ravvy.gymtrack.endereco.mapper.EnderecoMapper;
import com.ravvy.gymtrack.endereco.repository.EnderecoRepository;
import com.ravvy.gymtrack.endereco.entity.Endereco;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoVinculoValidator enderecoVinculoValidator;

    public EnderecoService(
            EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper, EnderecoVinculoValidator enderecoVinculoValidator
    ) {
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
        this.enderecoVinculoValidator = enderecoVinculoValidator;
    }

    @Transactional
    public EnderecoResponse save(
            EnderecoCreateRequest request
    ) {
        Endereco endereco = enderecoMapper.toEntity(request);

        enderecoVinculoValidator.garantirDisponivel(endereco.getId());

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