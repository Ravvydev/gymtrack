package com.ravvy.gymtrack.dto;

import com.ravvy.gymtrack.util.Email;
import com.ravvy.gymtrack.util.Telefone;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProfessorResponseDTO {

    private Long id;
    private String nome;
    private Integer idade;
    private Email email;
    private Telefone  telefone;

    public String getEmail(){
        return email.getEmail();
    }

    public String getTelefone(){
        return telefone.getNumero();
    }

}
