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
import br.luahr.topicos1.dto.EnderecoDTO;
import br.luahr.topicos1.dto.EnderecoResponseDTO;
import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.repository.EnderecoRepository;
import br.luahr.topicos1.repository.MunicipioRepository;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {
    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    Validator validator;

    @Override
    public List<EnderecoResponseDTO> getAll(int page, int pageSize) {
        List<Endereco> list = enderecoRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> EnderecoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public EnderecoResponseDTO findById(Long id) {
        Endereco endereco = enderecoRepository.findById(id);
        if(endereco == null)
            throw new NotFoundException("Endereco não encontrado.");
        return EnderecoResponseDTO.valueOf(endereco);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO create(EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        //validar(enderecoDTO);

        Endereco entity = new Endereco();

        entity.setBairro(enderecoDTO.bairro());
        entity.setNumero(enderecoDTO.numero());
        entity.setComplemento(enderecoDTO.complemento());
        entity.setCep(enderecoDTO.cep());

        enderecoRepository.persist(entity);

        return EnderecoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public EnderecoResponseDTO update(Long id, EnderecoDTO enderecoDTO) throws ConstraintViolationException {
        validar(enderecoDTO);

        Endereco entity = enderecoRepository.findById(id);
        
        entity.setBairro(enderecoDTO.bairro());
        entity.setNumero(enderecoDTO.numero());
        entity.setComplemento(enderecoDTO.complemento());
        entity.setCep(enderecoDTO.cep());
        
        return EnderecoResponseDTO.valueOf(entity);
    }

    private void validar(EnderecoDTO enderecoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<EnderecoDTO>> violations = validator.validate(enderecoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        enderecoRepository.deleteById(id);
    }

    @Override
    public long count() {
        return enderecoRepository.count();
    }

    @Override
    public List<EnderecoResponseDTO> findByCep(String cep, int page, int pageSize) {
        List<Endereco> list = enderecoRepository.findByCep(cep).page(page, pageSize).list();
        return list.stream().map(e-> EnderecoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public long countByCep(String cep) {
        return enderecoRepository.findByCep(cep).count();
    }

}