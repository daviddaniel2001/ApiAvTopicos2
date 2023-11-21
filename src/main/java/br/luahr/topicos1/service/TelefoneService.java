package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.TelefoneDTO;
import br.luahr.topicos1.dto.TelefoneResponseDTO;
import jakarta.validation.Valid;

public interface TelefoneService {
    List<TelefoneResponseDTO> getAll(int page, int pageSize);

    TelefoneResponseDTO findById(Long id);

    TelefoneResponseDTO create(@Valid TelefoneDTO productDTO);

    TelefoneResponseDTO update(Long id, TelefoneDTO productDTO);

    void delete(Long id);

    List<TelefoneResponseDTO> findByNumero(String numero, int page, int pageSize);

    long count();

    long countByNumero(String numero);
}
