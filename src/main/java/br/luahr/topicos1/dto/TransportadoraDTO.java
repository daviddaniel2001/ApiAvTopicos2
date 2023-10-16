package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.model.Telefone;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TransportadoraDTO(

    @NotBlank(message = "O nome deve ser inserido..")
    String nome, 

    @NotBlank(message = "O telefone deve ser inserido.")
    @Size(max= 8, message = "O telefone deve ter no maximo 8 digitos.")
    Telefone telefone,

    @NotBlank(message = "O endereco deve ser inserido.")
    Endereco endereco, 

    @NotBlank(message = "O metodo de entrega deve ser informado.")
    String metodoEntrega,
    
    @NotBlank(message = "O valor da entrega deve ser informado.")
    Integer valorEntrega,

    @NotBlank(message = "O tempo para a entrega deve ser informado.")
    String tempoEstimado,

    Compra compra

) {
    
}
