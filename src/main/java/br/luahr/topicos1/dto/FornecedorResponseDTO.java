package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Fornecedor;

public record FornecedorResponseDTO(
        Long id,
        String nome,
        String pais,
        String safra,
        Float volume) {
    public static FornecedorResponseDTO valueOf(Fornecedor fornecedor) {
        return new FornecedorResponseDTO(
            fornecedor.getId(),
            fornecedor.getNome(),
            fornecedor.getPais(),
            fornecedor.getSafra(),
            fornecedor.getVolume()
        );
    }
}