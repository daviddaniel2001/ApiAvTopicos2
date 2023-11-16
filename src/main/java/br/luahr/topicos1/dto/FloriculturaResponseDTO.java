package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Floricultura;

public record FloriculturaResponseDTO(

        Long id,
        String nome,
        String cnpj) {
    public static FloriculturaResponseDTO valueOf(Floricultura floricultura) {
        return new FloriculturaResponseDTO(
                floricultura.getId(),
                floricultura.getNome(),
                floricultura.getCnpj());
    }
}
