package com.ravvy.gymtrack.dto.mapper;

import com.ravvy.gymtrack.dto.AlunoCreateRequest;
import com.ravvy.gymtrack.dto.AlunoResponse;
import com.ravvy.gymtrack.dto.AlunoUpdateRequest;
import com.ravvy.gymtrack.model.Aluno;
import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoCreateRequest dto,
                          Endereco endereco,
                          Professor professor,
                          Instituicao instituicao) {

        Aluno aluno = new Aluno();

        aluno.setNome(dto.nome());

        aluno.setCpf(dto.cpf());

        aluno.setSexo(dto.sexo());
        aluno.setDataNascimento(dto.dataNascimento());

        aluno.setEmail(
                new Email(
                        dto.email(),
                        dto.senha()
                )
        );

        aluno.setEndereco(endereco);

        aluno.setInstituicao(instituicao);

        aluno.setProfessor(professor);

        aluno.setTelefone(
                new Telefone(
                        dto.telefone()
                )
        );

        return aluno;
    }

    public AlunoResponse toResponse(Aluno aluno) {

        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getDataNascimento(),
                aluno.getSexo(),
                aluno.getEmail().getEmail(),
                aluno.getTelefone().getNumero(),
                aluno.getEndereco().getLocalizacao(),
                aluno.getInstituicao().getNome()
        );
    }

    public void updateEntity(
            Aluno aluno,
            AlunoUpdateRequest request,
            Endereco endereco,
            Professor professor,
            Instituicao instituicao) {

        aluno.setNome(request.nome());
        aluno.setDataNascimento(request.dataNascimento());
        aluno.setSexo(request.sexo());

        aluno.setTelefone(
                new Telefone(
                        request.telefone()
                )
        );

        aluno.setEndereco(endereco);
        aluno.setProfessor(professor);
        aluno.setInstituicao(instituicao);
    }

}