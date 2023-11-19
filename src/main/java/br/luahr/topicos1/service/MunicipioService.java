package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.MunicipioDTO;
import br.luahr.topicos1.dto.MunicipioResponseDTO;
import jakarta.validation.Valid;

public interface MunicipioService {
    
    List<MunicipioResponseDTO> getAll(int page, int pageSize);

    MunicipioResponseDTO findById(Long id);

    MunicipioResponseDTO create(@Valid MunicipioDTO productDTO);

    MunicipioResponseDTO update(Long id, MunicipioDTO productDTO);

    void delete(Long id);

    List<MunicipioResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
