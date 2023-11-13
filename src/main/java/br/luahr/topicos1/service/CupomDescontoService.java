package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.CupomDescontoDTO;
import br.luahr.topicos1.dto.CupomDescontoResponseDTO;
import jakarta.validation.Valid;

public interface CupomDescontoService {
    
    List<CupomDescontoResponseDTO> getAll(int page, int pageSize);

    CupomDescontoResponseDTO findById (Long id);

    CupomDescontoResponseDTO create (@Valid CupomDescontoDTO cupomDescontoDTO);

    CupomDescontoResponseDTO update (Long id, CupomDescontoDTO cupomDescontoDTO);

    void delete (Long id);

    List<CupomDescontoResponseDTO> findByValorDesconto(String valorDesconto, int page, int pageSize);

    long count();

    long countByValorDesconto(String valorDesconto);
}
