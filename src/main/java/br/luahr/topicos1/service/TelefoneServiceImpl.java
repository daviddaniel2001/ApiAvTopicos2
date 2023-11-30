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
import br.luahr.topicos1.dto.TelefoneDTO;
import br.luahr.topicos1.dto.TelefoneResponseDTO;
import br.luahr.topicos1.model.Telefone;
import br.luahr.topicos1.repository.TelefoneRepository;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService{

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    Validator validator;
    
    @Override
    public List<TelefoneResponseDTO> getAll(int page, int pageSize) {
        List<Telefone> list = telefoneRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> TelefoneResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public TelefoneResponseDTO findById(Long id) {
        Telefone telefone = telefoneRepository.findById(id);
        if(telefone == null)
            throw new NotFoundException("Telefone não encontrado.");
        return TelefoneResponseDTO.valueOf(telefone);
    }

    @Override
    @Transactional
    public TelefoneResponseDTO create(TelefoneDTO telefoneDTO) throws ConstraintViolationException{
        //validar(telefoneDTO);

        Telefone entity = new Telefone();
        entity.setCodigoArea(telefoneDTO.codigoArea());
        entity.setNumero(telefoneDTO.numero());

        telefoneRepository.persist(entity);

        return TelefoneResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public TelefoneResponseDTO update(Long id, TelefoneDTO telefoneDTO) throws ConstraintViolationException{
        validar(telefoneDTO);

        Telefone entity = telefoneRepository.findById(id);
        entity.setCodigoArea(telefoneDTO.codigoArea());
        entity.setNumero(telefoneDTO.numero());

        return TelefoneResponseDTO.valueOf(entity);
    }

    private void validar(TelefoneDTO telefoneDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<TelefoneDTO>> violations = validator.validate(telefoneDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException{
        telefoneRepository.deleteById(id);
    }

    @Override
    public long count() {
        return telefoneRepository.count();
    }

    @Override
    public long countByNumero(String numero) {
        return telefoneRepository.findByNumero(numero).count();
    }

    @Override
    public List<TelefoneResponseDTO> findByNumero(String numero, int page, int pageSize) {
        List<Telefone> list = telefoneRepository.findByNumero(numero).page(page, pageSize).list();
        return list.stream().map(e-> TelefoneResponseDTO.valueOf(e)).collect(Collectors.toList()); 
    }
}
