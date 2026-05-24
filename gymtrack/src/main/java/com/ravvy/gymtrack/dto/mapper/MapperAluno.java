package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.AlunoRequestDTO;
import com.ravvy.gymtrack.dto.AlunoResponseDTO;
import com.ravvy.gymtrack.model.Aluno;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapperAluno {

    public Aluno requestToEntity(AlunoRequestDTO requestDTO) {
        Aluno aluno = new Aluno();
        aluno.setNome(requestDTO.getNome());
        aluno.setCpf(requestDTO.getCpf());
        aluno.setEmail(requestDTO.getEmail());
        aluno.setIdade(requestDTO.getIdade());
        aluno.setSexo(requestDTO.getSexo());
        aluno.setInstituicao(requestDTO.getInstituicao());
        aluno.setTelefone(requestDTO.getTelefone());
        return aluno;
    }

    public AlunoResponseDTO entityToResponseDTO(Aluno aluno) {
        AlunoResponseDTO alunoResponseDTO = new AlunoResponseDTO();
        alunoResponseDTO.setNome(aluno.getNome());
        alunoResponseDTO.setId(aluno.getId());
        alunoResponseDTO.setEmail(aluno.getEmail());
        alunoResponseDTO.setTelefone(aluno.getTelefone());
        alunoResponseDTO.setIdade(aluno.getIdade());
        return alunoResponseDTO;
    }

}
