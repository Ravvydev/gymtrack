package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.model.Instituicao;
import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import com.ravvy.gymtrack.util.TipoSexo;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfessorRequestDTO {

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
    private TipoSexo sexo;

    @NotBlank
    private Instituicao instituicao;

}
