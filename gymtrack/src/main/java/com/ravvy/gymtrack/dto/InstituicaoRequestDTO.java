package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstituicaoRequestDTO {

    private Long id;
    private String nome;
    private Endereco endereco;
    private Telefone telefone;
    private Email email;

}
