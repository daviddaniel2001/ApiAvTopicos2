package br.luahr.topicos1.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FloriculturaDTO(

    @NotBlank(message = "O nome precisa ser informado.")
    @Size(max = 100, message = "No maximo 100 caracteres.")
    String nome, 

    @NotBlank(message = "O cnpj precisa ser informado.")
    @Size(max = 14, message = "No maximo 14 caracteres.")
    String cnpj
) {
    
}
