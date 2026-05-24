package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Endereco;
import com.ravvy.gymtrack.util.Telefone;

public class InstituicaoResponseDTO {

    private String nome;
    private Email email;
    private Telefone telefone;
    private Endereco endereco;

    private String getEmail() {
        return email.getEmail();
    }

    private String getTelefone() {
        return telefone.getNumero();
    }

    private String getEndereco() {
        return endereco.getLocalizacao();
    }

}
