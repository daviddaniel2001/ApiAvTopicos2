package br.luahr.topicos1.dto;

import java.util.List;

import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Pedido;
import br.luahr.topicos1.model.Usuario;

public record CompraResponseDTO(
        Long id,
        Double totalCompra,
        Usuario usuario,
        Flor itemFlor,
        Integer quantidadeProduto,
        Pedido pedido 
) {

    public static CompraResponseDTO valueOf(Compra compra) {
        return new CompraResponseDTO(
                compra.getId(),
                compra.getTotalCompra(),
                compra.getUsuario(),
                compra.getItemFlor(),
                compra.getQuantidadeProduto(),
                compra.getPedido());
    }

    public static List<CompraResponseDTO> valueOf(List<Compra> item) {
        return item.stream().map(i -> CompraResponseDTO.valueOf(i)).toList();
     }
}