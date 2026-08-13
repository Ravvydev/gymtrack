package com.ravvy.gymtrack.dto;

import java.util.List;

public record AvaliacoesDoDiaResponse(
        Integer quantidade,
        List<AvaliacaoResponse> listAvaliacoes
) {
}
