package br.luahr.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.luahr.topicos1.dto.FloriculturaDTO;
import br.luahr.topicos1.dto.FloriculturaResponseDTO;
import br.luahr.topicos1.model.Floricultura;
import br.luahr.topicos1.repository.FloriculturaRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FloriculturaServiceImpl implements FloriculturaService {

    @Inject
    FloriculturaRepository floriculturaRepository;

    @Inject
    Validator validator;

    @Override
    public List<FloriculturaResponseDTO> getAll(int page, int pageSize) {

        List<Floricultura> list = floriculturaRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> FloriculturaResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public FloriculturaResponseDTO findById(Long id) {
        Floricultura floricultura = floriculturaRepository.findById(id);
        if (floricultura == null)
            throw new NotFoundException("Floricultura não encontrada.");
        return FloriculturaResponseDTO.valueOf(floricultura);
    }

    @Override
    public FloriculturaResponseDTO create(@Valid FloriculturaDTO floriculturaDTO) throws ConstraintViolationException {
        // validar (floriculturaDTO);

        Floricultura entity = new Floricultura();
        entity.setNome(floriculturaDTO.nome());
        entity.setCnpj(floriculturaDTO.cnpj());

        floriculturaRepository.persist(entity);

        return FloriculturaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public FloriculturaResponseDTO update(Long id, FloriculturaDTO floriculturaDTO)
            throws ConstraintViolationException {
        validar(floriculturaDTO);

        Floricultura entity = floriculturaRepository.findById(id);

        entity.setNome(floriculturaDTO.nome());
        entity.setCnpj(floriculturaDTO.cnpj());

        return FloriculturaResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        floriculturaRepository.deleteById(id);
    }

    @Override
    public long count() {
        return floriculturaRepository.count();
    }

    @Override
    public List<FloriculturaResponseDTO> findByNome(String nome, int page, int pageSize) {
         List<Floricultura> list = floriculturaRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> FloriculturaResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    private void validar(FloriculturaDTO floriculturaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FloriculturaDTO>> violations = validator.validate(floriculturaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    public long countByNome(String nome) {
        return floriculturaRepository.findByNome(nome).count();
    }
}
