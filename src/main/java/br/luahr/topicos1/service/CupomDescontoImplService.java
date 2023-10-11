package br.luahr.topicos1.service;



import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.luahr.topicos1.dto.CupomDescontoDTO;
import br.luahr.topicos1.dto.CupomDescontoResponseDTO;
import br.luahr.topicos1.model.CupomDesconto;
import br.luahr.topicos1.repository.CupomDescontoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class CupomDescontoImplService implements CupomDescontoService {

    @Inject
    CupomDescontoRepository cupomDescontoRepository;

    @Inject
    Validator validator;

    @Override
    public List<CupomDescontoResponseDTO> getAll() {
        return cupomDescontoRepository.findAll()
                .stream()
                .map(CupomDescontoResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public CupomDescontoResponseDTO findById(Long id) {
        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id);
        if (cupomDesconto == null)
            throw new NotFoundException("Não encontrado.");
        return new CupomDescontoResponseDTO(cupomDesconto);
    }

    @Override
    public CupomDescontoResponseDTO create(CupomDescontoDTO cupomDescontoDTO) {
        validar(cupomDescontoDTO);

        var entity = new CupomDesconto();

        entity.setCodigo(cupomDescontoDTO.codigo());
        entity.setValorDesconto(cupomDescontoDTO.valorDesconto());
        entity.setValidade(cupomDescontoDTO.validade());
        entity.setCompra(cupomDescontoDTO.compra());

        cupomDescontoRepository.persist(entity);

        return new CupomDescontoResponseDTO(entity);
    }

    @Override
    public CupomDescontoResponseDTO update(Long id, CupomDescontoDTO cupomDescontoDTO) {
        validar(cupomDescontoDTO);

        CupomDesconto entity = cupomDescontoRepository.findById(id);
        entity.setCodigo(cupomDescontoDTO.codigo());
        entity.setValorDesconto(cupomDescontoDTO.valorDesconto());
        entity.setValidade(cupomDescontoDTO.validade());
        entity.setCompra(cupomDescontoDTO.compra());

        return new CupomDescontoResponseDTO(entity);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id);

        if (cupomDescontoRepository.isPersistent(cupomDesconto)) {
            cupomDescontoRepository.delete(cupomDesconto);
        } else
            throw new NotFoundException("Nenhum cupom encontrado.");
    }

    @Override
    public long count() {
       return cupomDescontoRepository.count();
    }

    private void validar(CupomDescontoDTO cupomDescontoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<CupomDescontoDTO>> violations = validator.validate(cupomDescontoDTO);

        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }
}
