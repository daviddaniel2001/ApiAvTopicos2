package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.FloriculturaDTO;
import br.luahr.topicos1.dto.FloriculturaResponseDTO;

public interface FloriculturaService {
    
    List<FloriculturaResponseDTO> getAll();

    FloriculturaResponseDTO findById(Long id);

    FloriculturaResponseDTO create (FloriculturaDTO floriculturaDTO);

    FloriculturaResponseDTO update (Long id, FloriculturaDTO floriculturaDTO);

    void delete(Long id);

    long count();
}
