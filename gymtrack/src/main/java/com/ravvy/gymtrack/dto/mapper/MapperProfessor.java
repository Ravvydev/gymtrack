package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.ProfessorRequestDTO;
import com.ravvy.gymtrack.dto.ProfessorResponseDTO;
import com.ravvy.gymtrack.model.Professor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MapperProfessor {

    public Professor requestToEntity(ProfessorRequestDTO requestDTO) {
        Professor professor = new Professor();
        professor.setNome(requestDTO.getNome());
        professor.setCpf(requestDTO.getCpf());
        professor.setEmail(requestDTO.getEmail());
        professor.setIdade(requestDTO.getIdade());
        professor.setSexo(requestDTO.getSexo());
        professor.setInstituicao(requestDTO.getInstituicao());
        professor.setTelefone(requestDTO.getTelefone());
        return professor;
    }

    public ProfessorResponseDTO entityToResponseDTO(Professor professor) {
        ProfessorResponseDTO professorResponseDTO = new ProfessorResponseDTO();
        professorResponseDTO.setNome(professor.getNome());
        professorResponseDTO.setId(professor.getId());
        professorResponseDTO.setEmail(professor.getEmail());
        professorResponseDTO.setTelefone(professor.getTelefone());
        professorResponseDTO.setIdade(professor.getIdade());
        return professorResponseDTO;
    }

}
