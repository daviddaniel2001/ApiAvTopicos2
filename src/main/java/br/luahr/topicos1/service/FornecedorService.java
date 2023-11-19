package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.FornecedorDTO;
import br.luahr.topicos1.dto.FornecedorResponseDTO;
import jakarta.validation.Valid;

public interface FornecedorService {
    // recursos basicos
    List<FornecedorResponseDTO> getAll(int page, int pageSize);

    FornecedorResponseDTO findById(Long id);

    FornecedorResponseDTO create(@Valid FornecedorDTO fornecedorDTO);

    FornecedorResponseDTO update(Long id, FornecedorDTO fornecedorDTO);

    void delete(Long id);

    List<FornecedorResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
