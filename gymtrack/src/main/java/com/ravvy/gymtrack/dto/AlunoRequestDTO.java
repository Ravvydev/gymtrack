package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.model.Professor;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import com.ravvy.gymtrack.util.TipoSexoBiologico;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlunoRequestDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private Integer idade;

    @NotBlank
    private String cpf;

    @NotBlank
    private Telefone telefone;

    private Email email;

    @NotBlank
    private Professor professor;

    @NotBlank
    private TipoSexoBiologico sexo;

    @NotBlank
    private Instituicao instituicao;

}
