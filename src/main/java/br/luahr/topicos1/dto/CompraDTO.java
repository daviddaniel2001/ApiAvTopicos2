package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompraDTO(

    @NotNull(message = "O total da compra deve ser inserido")
    Double totalCompra,

    @NotBlank(message = "O usuario precisa ser informado.")
    Usuario usuario,

    @NotBlank(message = "O produto deve ser informado.")
    Long itemFlor, 

    @NotBlank(message = "A quantidade precisa ser informada.")
    Integer quantidadeProduto
)
{

}
