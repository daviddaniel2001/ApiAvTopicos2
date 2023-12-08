package br.luahr.topicos1.dto;

import java.time.LocalDateTime;
import java.util.List;

import br.luahr.topicos1.model.Pedido;


public record PedidoResponseDTO(
    Long id,
    UsuarioResponseDTO usuario,
    LocalDateTime dataPedido,
    List<CompraResponseDTO> itens,
    Double totalPedido
) {
    
    public static PedidoResponseDTO valueOf(Pedido pedido){
        return new PedidoResponseDTO(
            pedido.getId(),
            UsuarioResponseDTO.valueOf(pedido.getUsuario()),
            pedido.getDataPedido(),
            CompraResponseDTO.valueOf(pedido.getItens()),
            pedido.getTotalPedido());
    }
}
