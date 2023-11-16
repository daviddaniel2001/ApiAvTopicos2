package br.luahr.topicos1.dto;

import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.model.Municipio;

public record EnderecoResponseDTO(
        Long id,
        String bairro,
        String numero,
        String complemento,
        String cep,
        Municipio municipio) {
    public static EnderecoResponseDTO valueOf(Endereco endereco) {
        return new EnderecoResponseDTO(
                endereco.getId(),
                endereco.getBairro(),
                endereco.getNumero(),
                endereco.getComplemento(),
                endereco.getCep(),
                endereco.getMunicipio());
    }
}
