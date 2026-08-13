package com.ravvy.gymtrack.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100,  nullable = false)
    private String rua;

    @Column(length = 100,  nullable = false)
    private String bairro;

    @Column(length = 50,  nullable = false)
    private String complemento;

    @Column(length = 100,  nullable = false)
    private String cidade;

    @Column(length = 100,  nullable = false)
    private String estado;

    @Column(nullable = false)
    private int numeroCasa;

    @Column(length = 8,  nullable = false)
    private String cep;

    @Column(length = 2,  nullable = false)
    private String uf;

    public String getLocalizacao() {
        return "Cidade: " + cidade +
                "\nUF: " + uf +
                "\nCep: " + cep +
                "\nRua: " + rua +
                "\nBairro: " + bairro +
                "\nNúmero: " + numeroCasa +
                "\nComplemento: " + complemento;
    }

}
