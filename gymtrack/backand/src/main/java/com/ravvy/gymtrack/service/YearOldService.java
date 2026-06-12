package com.ravvy.gymtrack.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class YearOldService {

    public static Integer calcularIdade(LocalDate dataNascimento) {

        if (dataNascimento == null) {
            throw new NullPointerException("dataNascimento é nulo");
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new  IllegalArgumentException("A data de nascimento é invalida!");
        }

        return Period.between(dataNascimento, LocalDate.now()).getYears();

    }

}
