
package com.ravvy.gymtrack.shared.exception;

public class SenhaAtualIncorretaException extends RuntimeException {

    public SenhaAtualIncorretaException() {
        super("A senha atual está incorreta.");
    }
}

