package com.ravvy.gymtrack.avaliacao.dto;

import java.util.List;

public record AvaliacoesDoDiaResponse(
        Integer quantidade,
        List<AvaliacaoResponse> listAvaliacoes
) {
}
