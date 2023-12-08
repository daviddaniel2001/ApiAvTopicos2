package br.luahr.topicos1.dto;

import java.util.List;

public record PedidoDTO(

    List<CompraDTO> itens
) {
    
}
