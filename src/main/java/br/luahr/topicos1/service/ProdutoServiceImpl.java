package br.luahr.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.luahr.topicos1.dto.ProdutoDTO;
import br.luahr.topicos1.dto.ProdutoResponseDTO;
import br.luahr.topicos1.model.Produto;
import br.luahr.topicos1.repository.ProdutoRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class ProdutoServiceImpl implements ProdutoService {

    @Inject
    ProdutoRepository produtoRepository;

    @Inject
    Validator validator;

    @Override
    public List<ProdutoResponseDTO> getAll(int page, int pageSize) {
        List<Produto> list = produtoRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> ProdutoResponseDTO.valueOf(e)).collect(Collectors.toList());

    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null)
            throw new NotFoundException("Produto não encontrado.");
        return ProdutoResponseDTO.valueOf(produto);
    }

    @Override
    public ProdutoResponseDTO create(ProdutoDTO produtoDTO) throws ConstraintViolationException {
        validar(produtoDTO);

        var entity = new Produto();
        entity.setNome(produtoDTO.nome());
        entity.setDescricao(produtoDTO.descricao());
        entity.setValorUnidade(produtoDTO.valorUnidade());
        entity.setNomeImagem(produtoDTO.nomeImagem());
        entity.setQuantEstoque(produtoDTO.quantEstoque());

        produtoRepository.persist(entity);

        return ProdutoResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public ProdutoResponseDTO update(Long id, ProdutoDTO produtoDTO) throws ConstraintViolationException {
        validar(produtoDTO);

        Produto entity = produtoRepository.findById(id);
        entity.setNome(produtoDTO.nome());
        entity.setDescricao(produtoDTO.descricao());
        entity.setValorUnidade(produtoDTO.valorUnidade());
        entity.setNomeImagem(produtoDTO.nomeImagem());
        entity.setQuantEstoque(produtoDTO.quantEstoque());

        return ProdutoResponseDTO.valueOf(entity);
    }

    private void validar(ProdutoDTO produtoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

    @Override
    public void delete(Long id) {
        produtoRepository.deleteById(id);
    }

    @Override
    public long count() {
        return produtoRepository.count();
    }

    @Override
    public List<ProdutoResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Produto> list = produtoRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> ProdutoResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public long countByNome(String nome) {
        return produtoRepository.findByNome(nome).count();
    }

}
