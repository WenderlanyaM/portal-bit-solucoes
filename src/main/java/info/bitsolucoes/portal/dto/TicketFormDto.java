package info.bitsolucoes.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketFormDto(
        @NotBlank(message = "O título é obrigatório.")
        String titulo,

        @NotBlank(message = "A descrição é obrigatória.")
        String descricao,

        @NotNull(message = "A categoria é obrigatória.")
        Long categoriaId
) {}