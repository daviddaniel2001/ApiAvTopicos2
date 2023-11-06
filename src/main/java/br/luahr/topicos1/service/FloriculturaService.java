package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.FloriculturaDTO;
import br.luahr.topicos1.dto.FloriculturaResponseDTO;
import jakarta.validation.Valid;

public interface FloriculturaService {
    
    List<FloriculturaResponseDTO> getAll(int page, int pageSize);

    FloriculturaResponseDTO findById(Long id);

    FloriculturaResponseDTO create (@Valid FloriculturaDTO floriculturaDTO);

    FloriculturaResponseDTO update (Long id, FloriculturaDTO floriculturaDTO);

    void delete(Long id);

    List<FloriculturaResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
