package com.ravvy.gymtrack.shared.exception;

public class EmailDuplicadoException extends RuntimeException {
    public EmailDuplicadoException() {
        super("O e-mail informado já está cadastrado no sistema.");
    }
}
