package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.EnderecoDTO;
import br.luahr.topicos1.dto.EnderecoResponseDTO;
import jakarta.validation.Valid;

public interface EnderecoService {
    // recursos basicos
    List<EnderecoResponseDTO> getAll(int page, int pageSize);

    EnderecoResponseDTO findById(Long id);

    EnderecoResponseDTO create(@Valid EnderecoDTO productDTO);

    EnderecoResponseDTO update(Long id, EnderecoDTO productDTO);

    void delete(Long id);

    // recursos extras

    List<EnderecoResponseDTO> findByCep(String cep, int page, int pageSize);

    long count();

    long countByCep(String cep);
}
