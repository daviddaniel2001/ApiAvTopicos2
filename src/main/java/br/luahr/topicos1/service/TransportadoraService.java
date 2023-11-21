package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.TransportadoraDTO;
import br.luahr.topicos1.dto.TransportadoraResponseDTO;
import jakarta.validation.Valid;

public interface TransportadoraService {
    
    List<TransportadoraResponseDTO> getAll(int page, int pageSize);

    TransportadoraResponseDTO findById(Long id);

    TransportadoraResponseDTO create (@Valid TransportadoraDTO transportadoraDTO);

    TransportadoraResponseDTO update (Long id, TransportadoraDTO transportadoraDTO);

    List<TransportadoraResponseDTO> findByNome(String nome, int page, int pageSize);

    void delete (Long id);

    long count();

    long countByNome(String nome);
}
