package br.luahr.topicos1.service;

import br.luahr.topicos1.dto.UsuarioResponseDTO;
import br.luahr.topicos1.model.Usuario;

public interface TokenJwtService {
    public String generateJwt(UsuarioResponseDTO usuario);
}
