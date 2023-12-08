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

import br.luahr.topicos1.dto.CompraDTO;
import br.luahr.topicos1.dto.CompraResponseDTO;
import br.luahr.topicos1.model.Compra;
import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.repository.UsuarioRepository;
import br.luahr.topicos1.repository.CompraRepository;
import br.luahr.topicos1.repository.FlorRepository;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {

    @Inject
    CompraRepository compraRepository;

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    FlorRepository florRepository;

    @Inject
    Validator validator;

    @Override
    public List<CompraResponseDTO> getAll(int page, int pageSize) {

        List<Compra> list = compraRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> CompraResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public CompraResponseDTO findById(Long id) {
        Compra compra = compraRepository.findById(id);
        if (compra == null)
            throw new NotFoundException("Compra não encontrada.");
        return CompraResponseDTO.valueOf(compra);
    }

    private void validar(CompraDTO compraDTO) throws ConstraintViolationException {
        Set<ConstraintViolation<CompraDTO>> violations = validator.validate(compraDTO);
        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    @Transactional
    public CompraResponseDTO create(CompraDTO compraDTO) throws ConstraintViolationException {
        // validar(compraDTO);

        Compra entity = new Compra();
        entity.setUsuario(compraDTO.usuario());
        Flor flor = florRepository.findById(compraDTO.itemFlor());
        entity.setItemFlor(flor);
        entity.setQuantidadeProduto(compraDTO.quantidadeProduto());

        compraRepository.persist(entity);

        return CompraResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public CompraResponseDTO update(Long id, CompraDTO compraDTO) {
        validar(compraDTO);

        Compra entity = compraRepository.findById(id);

        entity.setUsuario(compraDTO.usuario());
        Flor flor = florRepository.findById(compraDTO.itemFlor());
        entity.setItemFlor(flor);
        entity.setQuantidadeProduto(compraDTO.quantidadeProduto());

        return CompraResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        compraRepository.deleteById(id);
    }

    @Override
    public long count() {
        return compraRepository.count();
    }

    @Override
    public List<CompraResponseDTO> findByItemFlor(Long itemFlor, int page, int pageSize) {

        List<Compra> list = compraRepository.findByItemFlor(itemFlor).page(page, pageSize).list();
        return list.stream().map(e -> CompraResponseDTO.valueOf(e)).collect(Collectors.toList());

    }

    @Override
    public long countByItemFlor(Long itemFlor) {
        return compraRepository.findByItemFlor(itemFlor).count();
    }
}
