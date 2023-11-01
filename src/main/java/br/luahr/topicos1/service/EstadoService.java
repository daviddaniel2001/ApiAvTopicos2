package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.EstadoDTO;
import br.luahr.topicos1.dto.EstadoResponseDTO;
import jakarta.validation.Valid;

public interface EstadoService {
    
    // recursos basicos
    List<EstadoResponseDTO> getAll(int page, int pageSize);

    EstadoResponseDTO findById(Long id);

    EstadoResponseDTO create(@Valid EstadoDTO productDTO);

    EstadoResponseDTO update(Long id, EstadoDTO productDTO);

    void delete(Long id);

    // recursos extras
    List<EstadoResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
