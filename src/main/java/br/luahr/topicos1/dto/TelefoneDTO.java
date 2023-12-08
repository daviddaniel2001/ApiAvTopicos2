package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Telefone;
import jakarta.validation.constraints.NotBlank;

public record TelefoneDTO(
    @NotBlank(message = "O campo precisa ser preenchido.")
    String codigoArea,
    @NotBlank(message = "O campo precisa ser preenchido.")
    String numero
) {
    public static TelefoneDTO valueOf(Telefone telefone) {
        return new TelefoneDTO(
                telefone.getCodigoArea(),
                telefone.getNumero());
    }
}