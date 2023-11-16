package br.luahr.topicos1.service;

import java.util.List;

import br.luahr.topicos1.dto.FlorDTO;
import br.luahr.topicos1.dto.FlorResponseDTO;
//import br.luahr.topicos1.model.Flor;
import jakarta.validation.Valid;

public interface FlorService {
    // recursos basicos
    List<FlorResponseDTO> getAll(int page, int pageSize);

    FlorResponseDTO findById(Long id);

    FlorResponseDTO create(@Valid FlorDTO productDTO);

    FlorResponseDTO update(Long id, FlorDTO productDTO);

    //Flor updateImg(Long id, String nomeImagem);

    void delete(Long id);

    List<FlorResponseDTO> findByNome(String nome, int page, int pageSize);

    long count();

    long countByNome(String nome);
}
