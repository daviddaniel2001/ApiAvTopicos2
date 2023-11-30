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

import br.luahr.topicos1.dto.FlorDTO;
import br.luahr.topicos1.dto.FlorResponseDTO;
import br.luahr.topicos1.model.Flor;
import br.luahr.topicos1.model.Fornecedor;
import br.luahr.topicos1.model.TipoFlor;
import br.luahr.topicos1.repository.FlorRepository;

@ApplicationScoped
public class FlorServiceImpl implements FlorService{

    @Inject
    FlorRepository florRepository;

    @Inject
    Validator validator;

    @Override
    public List<FlorResponseDTO> getAll(int page, int pageSize) {
        List<Flor> list = florRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> FlorResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public FlorResponseDTO findById(Long id) {
        Flor flor = florRepository.findById(id);
        if(flor == null)
            throw new NotFoundException("Flor não encontrada.");
        return FlorResponseDTO.valueOf(flor);
    }

    @Override
    @Transactional
    public FlorResponseDTO create(FlorDTO florDTO) {
        //validar(florDTO);

        Flor entity = new Flor();

        entity.setNome(florDTO.nome());
        entity.setDescricao(florDTO.descricao());
        entity.setValorUnidade(florDTO.valorUnidade());
        entity.setCorPetalas(florDTO.corPetalas());
        entity.setAlturaCaule(florDTO.alturaCaule());
        entity.setTipoFlor(TipoFlor.valueOf(florDTO.tipoFlor()));
        entity.setFornecedor(new Fornecedor());
        entity.getFornecedor().setId(florDTO.idFornecedor());

        florRepository.persist(entity);

        return FlorResponseDTO.valueOf(entity);

    }

    @Override
    @Transactional
    public FlorResponseDTO update(Long id, FlorDTO florDTO) throws ConstraintViolationException {
        validar(florDTO);
        
        Flor entity = florRepository.findById(id);

        entity.setNome(florDTO.nome());
        entity.setDescricao(florDTO.descricao());
        entity.setValorUnidade(florDTO.valorUnidade());
        entity.setCorPetalas(florDTO.corPetalas());
        entity.setAlturaCaule(florDTO.alturaCaule());
        entity.setTipoFlor(TipoFlor.valueOf(florDTO.tipoFlor()));
        entity.setFornecedor(new Fornecedor());
        entity.getFornecedor().setId(florDTO.idFornecedor());

        return FlorResponseDTO.valueOf(entity);

    }

    private void validar(FlorDTO florDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<FlorDTO>> violations = validator.validate(florDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException{
        florRepository.deleteById(id);
    }

    @Override
    public long count() {
        return florRepository.count();
    }
    
    @Override
    public List<FlorResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Flor> list = florRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> FlorResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public long countByNome(String nome) {
        return florRepository.findByNome(nome).count();
    }

    @Override
    public FlorResponseDTO saveImage(Long id, String nomeImagem) {

        Flor entity = florRepository.findById(id);
        entity.setNomeImagem(nomeImagem);

        return FlorResponseDTO.valueOf(entity);
    }
}
