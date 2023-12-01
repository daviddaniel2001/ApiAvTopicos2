package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.FlorDTO;
import br.luahr.topicos1.dto.FlorResponseDTO;
import jakarta.validation.Valid;
public interface FlorService {

    List<FlorResponseDTO> getAll(int page, int pageSize);

    FlorResponseDTO findById(Long id);

    FlorResponseDTO create(@Valid FlorDTO productDTO);

    FlorResponseDTO update(Long id, FlorDTO productDTO);

    FlorResponseDTO saveImage(Long id, String nomeImagem);

    void delete(Long id);

    List<FlorResponseDTO> findByNome(String nome, int page, int pageSize);
    
    public byte[] createReportFlor(String filter);

    long count();

    long countByNome(String nome);
}
