package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.PedidoDTO;
import br.luahr.topicos1.dto.PedidoResponseDTO;

public interface PedidoService {
    
    public PedidoResponseDTO insert(PedidoDTO pedidoDto, String login);
    public PedidoResponseDTO findById(Long id );
    public List<PedidoResponseDTO> findByAll();
    public List<PedidoResponseDTO> findByAll(String login);
}
