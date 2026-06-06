package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.ProfessorCreateRequest;
import com.ravvy.gymtrack.dto.ProfessorResponse;
import com.ravvy.gymtrack.dto.ProfessorUpdateRequest;
import com.ravvy.gymtrack.dto.UpdateSenha;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.service.EnderecoService;
import com.ravvy.gymtrack.service.InstituicaoService;
import com.ravvy.gymtrack.service.YearOldService;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
public class ProfessorMapper {

    public Professor toEntity(ProfessorCreateRequest requestDTO,
                              Endereco endereco,
                              Instituicao instituicao) {

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

        professor.setEndereco(endereco);

        professor.setInstituicao(instituicao);

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

    public void updateEntity(ProfessorUpdateRequest request,
                                          Professor professor, Endereco endereco, Instituicao instituicao) {

        professor.setNome(request.nome());
        professor.setDataNascimento(request.dataNascimento());
        professor.setSexo(request.sexo());

        professor.setTelefone(
                new Telefone(
                        request.telefone()
                    )
                );

        professor.setEndereco(endereco);
        professor.setInstituicao(instituicao);

    }

}
