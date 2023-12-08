package br.luahr.topicos1.service;

import br.luahr.topicos1.dto.UsuarioResponseDTO;

public interface TokenJwtService {
    public String generateJwt(UsuarioResponseDTO usuario);
}
