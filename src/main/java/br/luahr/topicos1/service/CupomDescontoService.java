package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.CupomDescontoDTO;
import br.luahr.topicos1.dto.CupomDescontoResponseDTO;

public interface CupomDescontoService {
    
    List<CupomDescontoResponseDTO> getAll();

    CupomDescontoResponseDTO findById (Long id);

    CupomDescontoResponseDTO create (CupomDescontoDTO cupomDescontoDTO);

    CupomDescontoResponseDTO update (Long id, CupomDescontoDTO cupomDescontoDTO);

    void delete (Long id);

    long count();
}
