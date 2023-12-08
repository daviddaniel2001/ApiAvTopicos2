package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Fornecedor;
import br.luahr.topicos1.model.TipoFlor;

public record FlorResponseDTO(
        Long id,
        String nome,
        String descricao,
        Double valorUnidade,
        String corPetalas,
        Float alturaCaule,
        TipoFlor tipoFlor,
        Fornecedor fornecedor,
        String nomeImagem,
        Integer estoque) {
    public static FlorResponseDTO valueOf(Flor flor) {
        return new FlorResponseDTO(
                flor.getId(),
                flor.getNome(),
                flor.getDescricao(),
                flor.getValorUnidade(),
                flor.getCorPetalas(),
                flor.getAlturaCaule(),
                flor.getTipoFlor(),
                flor.getFornecedor(),
                flor.getNomeImagem(),
                flor.getEstoque());
    }
}
