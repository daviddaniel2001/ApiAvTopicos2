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
import jakarta.transaction.Transactional;
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
    public List<CupomDescontoResponseDTO> getAll(int page, int pageSize) {
        List<CupomDesconto> list = cupomDescontoRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> CupomDescontoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public CupomDescontoResponseDTO findById(Long id) {
        CupomDesconto cupomDesconto = cupomDescontoRepository.findById(id);
        if(cupomDesconto == null)
            throw new NotFoundException("Cupom não encontrado.");
        return CupomDescontoResponseDTO.valueOf(cupomDesconto);
    }

    @Override
    public CupomDescontoResponseDTO create(CupomDescontoDTO cupomDescontoDTO) {
        //validar(cupomDescontoDTO);

        CupomDesconto entity = new CupomDesconto();

        entity.setCodigo(cupomDescontoDTO.codigo());
        entity.setValorDesconto(cupomDescontoDTO.valorDesconto());
        entity.setValidade(cupomDescontoDTO.validade());
        
        cupomDescontoRepository.persist(entity);

        return CupomDescontoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CupomDescontoResponseDTO update(Long id, CupomDescontoDTO cupomDescontoDTO) {
        validar(cupomDescontoDTO);

        CupomDesconto entity = cupomDescontoRepository.findById(id);

        entity.setCodigo(cupomDescontoDTO.codigo());
        entity.setValorDesconto(cupomDescontoDTO.valorDesconto());
        entity.setValidade(cupomDescontoDTO.validade());

        return CupomDescontoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        cupomDescontoRepository.deleteById(id);
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

    @Override
    public List<CupomDescontoResponseDTO> findByValorDesconto(String valorDesconto, int page, int pageSize) {
        List<CupomDesconto> list = cupomDescontoRepository.findByValorDesconto(valorDesconto).page(page, pageSize).list();
        return list.stream().map(e-> CupomDescontoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public long countByValorDesconto(String valorDesconto) {
        return cupomDescontoRepository.findByValorDesconto(valorDesconto).count();
    }
}
