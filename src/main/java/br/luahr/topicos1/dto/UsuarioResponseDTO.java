package br.luahr.topicos1.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.luahr.topicos1.model.Usuario;
import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.model.Perfil;
import br.luahr.topicos1.model.Sexo;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String login,
        String cpf,
        @JsonInclude(JsonInclude.Include.NON_NULL) Sexo sexo,
        List<TelefoneDTO> listaTelefone,
        Endereco endereco,
        String nomeImagem,
        Set<Perfil> perfil) {
    public static UsuarioResponseDTO valueOf(Usuario usuario) {
        if (usuario == null)
            return null;

        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getLogin(),
                usuario.getCpf(),
                usuario.getSexo(),
                usuario.getListaTelefone().stream()
                        .map(t -> TelefoneDTO.valueOf(t)).toList(),
                usuario.getEndereco(),
                usuario.getNomeImagem(),
                usuario.getPerfis()
                );
    }
}