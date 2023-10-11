package br.luahr.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.luahr.topicos1.dto.TransportadoraDTO;
import br.luahr.topicos1.dto.TransportadoraResponseDTO;
import br.luahr.topicos1.model.Transportadora;
import br.luahr.topicos1.repository.TransportadoraRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class TransportadoraImplService implements TransportadoraService{

    @Inject
    TransportadoraRepository transportadoraRepository;

    @Inject
    Validator validator;

    @Override
    public List<TransportadoraResponseDTO> getAll() {
        return transportadoraRepository.findAll()
                .stream()
                .map(TransportadoraResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public TransportadoraResponseDTO findById(Long id) {
        Transportadora transportadora = transportadoraRepository.findById(id);
        if(transportadora == null)
            throw new NotFoundException("Não encontrado.");
        return new TransportadoraResponseDTO(transportadora);
    }

    @Override
    public TransportadoraResponseDTO create(TransportadoraDTO transportadoraDTO) {
        validar(transportadoraDTO);

        var entity = new Transportadora();
        entity.setNome(transportadoraDTO.nome());
        entity.setTelefone(transportadoraDTO.telefone());
        entity.setEndereco(transportadoraDTO.endereco());
        entity.setMetodoEntrega(transportadoraDTO.metodoEntrega());
        entity.setValorEntrega(transportadoraDTO.valorEntrega());
        entity.setTempoEstimado(transportadoraDTO.tempoEstimado());

        return new TransportadoraResponseDTO(entity);
    }

    @Override
    public TransportadoraResponseDTO update(Long id, TransportadoraDTO transportadoraDTO) {
        validar(transportadoraDTO);

        Transportadora entity = transportadoraRepository.findById(id);
        entity.setNome(transportadoraDTO.nome());
        entity.setTelefone(transportadoraDTO.telefone());
        entity.setEndereco(transportadoraDTO.endereco());
        entity.setMetodoEntrega(transportadoraDTO.metodoEntrega());
        entity.setValorEntrega(transportadoraDTO.valorEntrega());
        entity.setTempoEstimado(transportadoraDTO.tempoEstimado());

        return new TransportadoraResponseDTO(entity);
    }

    @Override
    public void delete(Long id) {
        if(id == null)
            throw new IllegalArgumentException("Numero invalido.");
        
        Transportadora transportadora = transportadoraRepository.findById(id);

        if(transportadoraRepository.isPersistent(transportadora)) {
            transportadoraRepository.delete(transportadora);
        } else 
             throw new NotFoundException("Nenhuma transportadora encontrada.");
    }

    @Override
    public long count() {
        return transportadoraRepository.count();
    }

    private void validar(TransportadoraDTO transportadoraDTO) throws ConstraintViolationException {
        
        Set<ConstraintViolation<TransportadoraDTO>> violations = validator.validate(transportadoraDTO);

        if(!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    
}
