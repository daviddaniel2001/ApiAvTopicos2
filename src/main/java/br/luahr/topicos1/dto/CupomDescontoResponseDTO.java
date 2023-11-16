package br.luahr.topicos1.dto;

import java.time.LocalDate;

import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.CupomDesconto;

public record CupomDescontoResponseDTO(

        Long id,
        String codigo,
        String valorDesconto,
        LocalDate validade,
        Compra compra) {
    public static CupomDescontoResponseDTO valueOf(CupomDesconto cupomDesconto) {
        return new CupomDescontoResponseDTO(
                cupomDesconto.getId(),
                cupomDesconto.getCodigo(),
                cupomDesconto.getValorDesconto(),
                cupomDesconto.getValidade(),
                cupomDesconto.getCompra());
    }
}
