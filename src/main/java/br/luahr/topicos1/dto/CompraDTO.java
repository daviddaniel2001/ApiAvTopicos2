package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Usuario;
import jakarta.validation.constraints.NotBlank;

public record CompraDTO(
    @NotBlank(message = "O usuario precisa ser informado.")
    Usuario usuario,

    @NotBlank(message = "O produto deve ser informado.")
    Flor itemProduto, 

    @NotBlank(message = "A quantidade precisa ser informada.")
    Integer quantidadeProduto
)
{

}
