package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Floricultura;

public record FloriculturaResponseDTO(

    Long id,
    String nome, 
    String cnpj
) {
    public FloriculturaResponseDTO(Floricultura floricultura){
        this(floricultura.getId(),
            floricultura.getNome(),
            floricultura.getCnpj()
            );
    }
}
