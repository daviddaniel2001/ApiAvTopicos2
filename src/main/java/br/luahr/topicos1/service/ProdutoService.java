package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.ProdutoDTO;
import br.luahr.topicos1.dto.ProdutoResponseDTO;

public interface ProdutoService {
    
    List<ProdutoResponseDTO> getAll();

    ProdutoResponseDTO findById(Long id);

    ProdutoResponseDTO create(ProdutoDTO produtoDTO);

    ProdutoResponseDTO update(Long id, ProdutoDTO produtoDTO);

    void delete(Long id);

    long count();
}
