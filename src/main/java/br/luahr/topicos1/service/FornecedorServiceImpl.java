package br.luahr.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import br.luahr.topicos1.dto.FornecedorDTO;
import br.luahr.topicos1.dto.FornecedorResponseDTO;
import br.luahr.topicos1.model.Fornecedor;
import br.luahr.topicos1.repository.FornecedorRepository;

@ApplicationScoped
public class FornecedorServiceImpl implements FornecedorService{

    @Inject
    FornecedorRepository fornecedorRepository;

    @Inject
    Validator validator;

    @Override
    public List<FornecedorResponseDTO> getAll(int page, int pageSize) {

        List<Fornecedor> list = fornecedorRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> FornecedorResponseDTO.valueOf(e)).collect(Collectors.toList());
    } 

    @Override
    public FornecedorResponseDTO findById(Long id) {

        Fornecedor fornecedor = fornecedorRepository.findById(id);
        if (fornecedor == null)
            throw new NotFoundException("Fornecedor não encontrada.");
        return FornecedorResponseDTO.valueOf(fornecedor);
    }

    @Override
    @Transactional
    public FornecedorResponseDTO create(FornecedorDTO fornecedorDTO) throws ConstraintViolationException {
        //validar(fornecedorDTO);

        Fornecedor entity = new Fornecedor();

        entity.setNome(fornecedorDTO.nome());
        entity.setPais(fornecedorDTO.pais());
        entity.setSafra(fornecedorDTO.safra());
        entity.setVolume(fornecedorDTO.volume());

        fornecedorRepository.persist(entity);

        return FornecedorResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public FornecedorResponseDTO update(Long id, FornecedorDTO fornecedorDTO) throws ConstraintViolationException{

        validar(fornecedorDTO);

        Fornecedor entity = fornecedorRepository.findById(id);

        entity.setNome(fornecedorDTO.nome());
        entity.setPais(fornecedorDTO.pais());
        entity.setSafra(fornecedorDTO.safra());
        entity.setVolume(fornecedorDTO.volume());

        return FornecedorResponseDTO.valueOf(entity);
    }

    private void validar(FornecedorDTO fornecedorDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FornecedorDTO>> violations = validator.validate(fornecedorDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException{
        fornecedorRepository.deleteById(id);
    }

    @Override
    public long count() {
        return fornecedorRepository.count();
    }

    @Override
    public List<FornecedorResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Fornecedor> list = fornecedorRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> FornecedorResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public long countByNome(String nome) {
        return fornecedorRepository.findByNome(nome).count();
    }
}
