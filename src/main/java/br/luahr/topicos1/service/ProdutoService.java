package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.ProdutoDTO;
import br.luahr.topicos1.dto.ProdutoResponseDTO;
import jakarta.validation.Valid;

public interface ProdutoService {
    
    List<ProdutoResponseDTO> getAll(int page, int pageSize);

    ProdutoResponseDTO findById(Long id);

    ProdutoResponseDTO create(@Valid ProdutoDTO produtoDTO);

    ProdutoResponseDTO update(Long id, ProdutoDTO produtoDTO);

    void delete(Long id);

    List<ProdutoResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
