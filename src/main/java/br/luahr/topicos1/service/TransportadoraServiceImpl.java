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
public class TransportadoraServiceImpl implements TransportadoraService {

    @Inject
    TransportadoraRepository transportadoraRepository;

    @Inject
    Validator validator;

    @Override
    public List<TransportadoraResponseDTO> getAll(int page, int pageSize) {
        List<Transportadora> list = transportadoraRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> TransportadoraResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public TransportadoraResponseDTO findById(Long id) {
        Transportadora transportadora = transportadoraRepository.findById(id);
        if (transportadora == null)
            throw new NotFoundException("Transportadora não encontrada.");
        return TransportadoraResponseDTO.valueOf(transportadora);
    }

    @Override
    public TransportadoraResponseDTO create(TransportadoraDTO transportadoraDTO) {
        // validar(transportadoraDTO);

        var entity = new Transportadora();
        entity.setNome(transportadoraDTO.nome());
        entity.setTelefone(transportadoraDTO.telefone());
        entity.setEndereco(transportadoraDTO.endereco());
        entity.setMetodoEntrega(transportadoraDTO.metodoEntrega());
        entity.setValorEntrega(transportadoraDTO.valorEntrega());
        entity.setTempoEstimado(transportadoraDTO.tempoEstimado());
        entity.setCompra(transportadoraDTO.compra());

        return TransportadoraResponseDTO.valueOf(entity);
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
        entity.setCompra(transportadoraDTO.compra());

        return TransportadoraResponseDTO.valueOf(entity);
    }

    private void validar(TransportadoraDTO transportadoraDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<TransportadoraDTO>> violations = validator.validate(transportadoraDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public void delete(Long id) {
        transportadoraRepository.deleteById(id);
    }

    @Override
    public long count() {
        return transportadoraRepository.count();
    }

    public long countByNome(String nome) {
        return transportadoraRepository.findByNome(nome).count();
    }

    @Override
    public List<TransportadoraResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Transportadora> list = transportadoraRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> TransportadoraResponseDTO.valueOf(e)).collect(Collectors.toList());
    }
}
