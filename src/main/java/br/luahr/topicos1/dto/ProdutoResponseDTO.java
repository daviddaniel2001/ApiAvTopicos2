package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Produto;

public record ProdutoResponseDTO(

    Long id, 
    String descricao, 
    Double valorUnidade, 
    String nomeImagem, 
    Integer quantEstoque

) {
    public ProdutoResponseDTO(Produto produto) {
        this(
            produto.getId(),
            produto.getDescricao(),
            produto.getValorUnidade(),
            produto.getNomeImagem(),
            produto.getQuantEstoque()
            );
    }
}
