package br.luahr.topicos1.dto;

import java.time.LocalDate;

import br.luahr.topicos1.model.Compra;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CupomDescontoDTO(

    @NotBlank(message = "O codigo de desconto deve ser informado.")
    @Size(max = 4, message = "O codigo possui apenas 4 caracteres.")
    String codigo, 

    @NotBlank(message = "O valor do desconto deve ser informado.")
    String valorDesconto, 

    @NotBlank(message = "A data de validade do cupom deve ser informado.")
    LocalDate validade, 

    Compra compra 
) {
    
}
