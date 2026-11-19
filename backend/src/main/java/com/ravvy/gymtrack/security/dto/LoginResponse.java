package com.ravvy.gymtrack.security.dto;

import com.ravvy.gymtrack.usuario.enums.PapelUsuario;

public record LoginResponse(
        String token,
        String tipo,
        long expiraEmSegundos,
        PapelUsuario papel
) {
}