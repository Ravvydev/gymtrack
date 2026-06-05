package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.dto.ProfessorResponse;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.EnderecoService;
import com.ravvy.gymtrack.service.InstituicaoService;
import com.ravvy.gymtrack.service.YearOldService;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    private final InstituicaoService instituicaoService;
    private final EnderecoService enderecoService;

    public ProfessorMapper(InstituicaoService instituicaoService, EnderecoService enderecoService) {
        this.instituicaoService = instituicaoService;
        this.enderecoService = enderecoService;
    }

    public Professor toEntity(ProfessorCreateRequest requestDTO) {

        Professor professor = new Professor();

        professor.setNome(requestDTO.nome());

        professor.setDataNascimento(requestDTO.dataNascimento());

        professor.setCpf(requestDTO.cpf());

        professor.setEmail(
                new Email(
                        requestDTO.email(),
                        requestDTO.senha()
                    )
                );


        professor.setSexo(requestDTO.sexo());

        professor.setInstituicao(
                instituicaoService.buscarPorId(requestDTO.instituicaoId()));

        professor.setEndereco(
                enderecoService.buscarPorId(requestDTO.enderecoId())
        );

        professor.setTelefone(requestDTO.telefone());

        return professor;
    }

    public ProfessorResponse toResponse(Professor professor) {

        return new ProfessorResponse(
                professor.getId(),
                professor.getNome(),
                YearOldService.calcularIdade(professor.getDataNascimento()),
                professor.getEmail().getEmail(),
                professor.getTelefone().getNumero(),
                professor.getSexo(),
                professor.getEndereco().getLocalizacao(),
                professor.getInstituicao().getNome()
        );

    }

}
