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
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class FloriculturaServiceImpl implements FloriculturaService {

    @Inject
    FloriculturaRepository floriculturaRepository;

    @Inject
    Validator validator;

    @Override
    public List<FloriculturaResponseDTO> getAll() {
        return floriculturaRepository.findAll()
                .stream()
                .map(FloriculturaResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public FloriculturaResponseDTO findById(Long id) {
        Floricultura floricultura = floriculturaRepository.findById(id);
        if (floricultura == null)
            throw new NotFoundException("Não encontrado.");
        return new FloriculturaResponseDTO(floricultura);
    }

    @Override
    public FloriculturaResponseDTO create(FloriculturaDTO floriculturaDTO) throws ConstraintViolationException {
        validar(floriculturaDTO);

        var entity = new Floricultura();
        entity.setNome(floriculturaDTO.nome());
        entity.setCnpj(floriculturaDTO.cnpj());

        floriculturaRepository.persist(entity);

        return new FloriculturaResponseDTO(entity);
    }

    @Override
    @Transactional
    public FloriculturaResponseDTO update(Long id, FloriculturaDTO floriculturaDTO)throws ConstraintViolationException {
        validar(floriculturaDTO);

        Floricultura entity = floriculturaRepository.findById(id);
        entity.setNome(floriculturaDTO.nome());
        entity.setCnpj(floriculturaDTO.cnpj());

        return new FloriculturaResponseDTO(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if(id == null)
           throw new IllegalArgumentException("Número inválido");

        Floricultura floricultura = floriculturaRepository.findById(id);

        if(floriculturaRepository.isPersistent(floricultura)) {
            floriculturaRepository.delete(floricultura);
        }else
            throw new NotFoundException("Nenhuma floricultura encontrada.");
    }

    @Override
    public long count() {
        return floriculturaRepository.count();
    }

    private void validar(FloriculturaDTO floriculturaDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FloriculturaDTO>> violations = validator.validate(floriculturaDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }
}
