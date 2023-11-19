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
import br.luahr.topicos1.dto.MunicipioDTO;
import br.luahr.topicos1.dto.MunicipioResponseDTO;
import br.luahr.topicos1.model.Municipio;
import br.luahr.topicos1.repository.EstadoRepository;
import br.luahr.topicos1.repository.MunicipioRepository;

@ApplicationScoped
public class MunicipioImplService implements MunicipioService{

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    EstadoRepository estadoRepository;

    @Inject
    Validator validator;
    
    @Override
    public List<MunicipioResponseDTO> getAll(int page, int pageSize) {
        List<Municipio> list = municipioRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> MunicipioResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public MunicipioResponseDTO findById(Long id) {
        Municipio municipio = municipioRepository.findById(id);
        if(municipio == null)
            throw new NotFoundException("Municipio não encontrado.");
        return MunicipioResponseDTO.valueOf(municipio);
    }

    @Override
    @Transactional
    public MunicipioResponseDTO create(MunicipioDTO municipioDTO) throws ConstraintViolationException{
        //validar(municipioDTO);

        Municipio entity = new Municipio();
        
        entity.setNome(municipioDTO.nome());
        entity.setId(municipioDTO.idEstado());

        municipioRepository.persist(entity);

        return MunicipioResponseDTO.valueOf(entity);

    }

    @Override
    @Transactional
    public MunicipioResponseDTO update(Long id, MunicipioDTO municipioDTO) throws ConstraintViolationException{
        validar(municipioDTO);

        Municipio entity = municipioRepository.findById(id);

        entity.setNome(municipioDTO.nome());
        entity.setId(municipioDTO.idEstado());

        return MunicipioResponseDTO.valueOf(entity);
    }

    private void validar(MunicipioDTO municipioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<MunicipioDTO>> violations = validator.validate(municipioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException{
        municipioRepository.deleteById(id);
    }

    @Override
    public long count() {
        return municipioRepository.count();
    }

        @Override
    public long countByNome(String nome) {
        return municipioRepository.findByNome(nome).count();
    }

    @Override
    public List<MunicipioResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Municipio> list = municipioRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e-> MunicipioResponseDTO.valueOf(e)).collect(Collectors.toList()); 
    }
}
