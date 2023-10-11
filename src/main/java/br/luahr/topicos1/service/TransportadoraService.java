package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.TransportadoraDTO;
import br.luahr.topicos1.dto.TransportadoraResponseDTO;

public interface TransportadoraService {
    
    List<TransportadoraResponseDTO> getAll();

    TransportadoraResponseDTO findById(Long id);

    TransportadoraResponseDTO create (TransportadoraDTO transportadoraDTO);

    TransportadoraResponseDTO update (Long id, TransportadoraDTO transportadoraDTO);

    void delete (Long id);

    long count();
}
