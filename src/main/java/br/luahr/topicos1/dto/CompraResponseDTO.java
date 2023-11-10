package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Usuario;

public record CompraResponseDTO(
    Long id,
    Usuario usuario,
    Flor itemProduto,
    Integer quantidadeProduto

){

    public static CompraResponseDTO valueOf(Compra compra) {
        return new CompraResponseDTO(
            compra.getId(),
            compra.getCliente(),
            compra.getItemProduto(),
            compra.getQuantidadeProduto()

        );
    }
}