package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.model.Telefone;
import br.luahr.topicos1.model.Transportadora;

public record TransportadoraResponseDTO(

        Long id,
        String nome,
        Telefone telefone,
        Endereco endereco,
        String metodoEntrega,
        Integer valorEntrega,
        String tempoEstimado,
        Compra compra) {
    public static TransportadoraResponseDTO valueOf(Transportadora transportadora) {
        return new TransportadoraResponseDTO(
                transportadora.getId(),
                transportadora.getNome(),
                transportadora.getTelefone(),
                transportadora.getEndereco(),
                transportadora.getMetodoEntrega(),
                transportadora.getValorEntrega(),
                transportadora.getTempoEstimado(),
                transportadora.getCompra());
    }
}
