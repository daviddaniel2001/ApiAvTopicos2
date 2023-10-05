package br.luahr.topicos1.dto;

public record ProdutoDTO(

    String nome, 
    String descricao, 
    Double valorUnidade,
    String nomeImagem, 
    Integer quantEstoque

) {
    
}
