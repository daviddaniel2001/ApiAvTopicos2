package br.luahr.topicos1.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.luahr.topicos1.model.Usuario;
import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.model.Sexo;
import br.luahr.topicos1.model.Telefone;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String login,
        String cpf,
        @JsonInclude(JsonInclude.Include.NON_NULL) Sexo sexo,
        Telefone telefone,
        Endereco endereco,
        String nomeImagem) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getCpf(),
                usuario.getSexo(),
                usuario.getTelefone(),
                usuario.getEndereco(),
                usuario.getNomeImagem());
    }
}