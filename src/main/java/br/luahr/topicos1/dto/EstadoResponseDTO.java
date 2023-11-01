package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Estado;

public record EstadoResponseDTO(
    Long id,
    String nome,
    String sigla
) {
    public static EstadoResponseDTO valueOf(Estado estado){
        return new EstadoResponseDTO(
            estado.getId(),
            estado.getNome(),
            estado.getSigla()
        );
    }
}
