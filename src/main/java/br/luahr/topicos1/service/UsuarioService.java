package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.UsuarioDTO;
import br.luahr.topicos1.dto.UsuarioResponseDTO;

public interface UsuarioService {
    
    public UsuarioResponseDTO insert(UsuarioDTO usuarioDto);

    public UsuarioResponseDTO update(UsuarioDTO usuarioDTO, Long id);

    public void delete(Long id);

    public UsuarioResponseDTO findById(Long id);

    public List<UsuarioResponseDTO> findByNome(String nome);

    public List<UsuarioResponseDTO> findByAll();

    public UsuarioResponseDTO findByLoginSenha(String login, String senha);
}
