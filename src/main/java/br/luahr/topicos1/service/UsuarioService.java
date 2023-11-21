package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.UsuarioDTO;
import br.luahr.topicos1.dto.UsuarioResponseDTO;
import br.luahr.topicos1.model.Usuario;
import jakarta.validation.Valid;

public interface UsuarioService {
    
    List<UsuarioResponseDTO> getAll(int page, int pageSize);

    UsuarioResponseDTO findById(Long id);

    UsuarioResponseDTO create(@Valid UsuarioDTO productDTO);

    UsuarioResponseDTO update(Long id, UsuarioDTO productDTO);

    Usuario findByLoginAndSenha(String login, String senha);

    UsuarioResponseDTO update(Long id, String nomeImagem);

    UsuarioResponseDTO findByLogin(String login);

    void delete(Long id);

    List<UsuarioResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
