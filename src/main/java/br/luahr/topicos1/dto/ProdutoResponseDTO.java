package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Produto;

public record ProdutoResponseDTO(

        Long id,
        String descricao,
        Double valorUnidade,
        String nomeImagem,
        Integer quantEstoque

) {
    public static ProdutoResponseDTO valueOf(Produto produto) {
        return new ProdutoResponseDTO(
                produto.getId(),
                produto.getDescricao(),
                produto.getValorUnidade(),
                produto.getNomeImagem(),
                produto.getQuantEstoque());
    }
}
