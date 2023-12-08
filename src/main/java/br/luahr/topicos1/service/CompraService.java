package br.luahr.topicos1.service;

import java.util.List;
import br.luahr.topicos1.dto.CompraDTO;
import br.luahr.topicos1.dto.CompraResponseDTO;
import jakarta.validation.Valid;

public interface CompraService {
    List<CompraResponseDTO> getAll(int page, int pageSize);

    CompraResponseDTO findById(Long id);

    CompraResponseDTO create(@Valid CompraDTO compradto);

    CompraResponseDTO update(Long id, CompraDTO compradto);

    void delete(Long id);

    List<CompraResponseDTO> findByItemFlor(Long itemProduto, int page, int pageSize);

    long count();

    long countByItemFlor(Long itemFlor);
}
