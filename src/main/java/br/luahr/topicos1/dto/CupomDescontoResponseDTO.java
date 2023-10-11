package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.CupomDesconto;

public record CupomDescontoResponseDTO(

    Long id, 
    String codigo, 
    String valorDesconto,
    String validade, 
    Compra compra
) {
    public CupomDescontoResponseDTO(CupomDesconto cupomDesconto) {
        this(cupomDesconto.getId(),
             cupomDesconto.getCodigo(),
             cupomDesconto.getValorDesconto(),
             cupomDesconto.getValidade(),
             cupomDesconto.getCompra()
             );
    }
}
