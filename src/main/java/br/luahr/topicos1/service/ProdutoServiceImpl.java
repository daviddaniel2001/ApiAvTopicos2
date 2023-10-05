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
    public List<ProdutoResponseDTO> getAll() {
        return produtoRepository.findAll()
                .stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());

    }

    @Override
    public ProdutoResponseDTO findById(Long id) {
        Produto produto = produtoRepository.findById(id);
        if (produto == null)
            throw new NotFoundException("Não encontrado.");
        return new ProdutoResponseDTO(produto);
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

        return new ProdutoResponseDTO(entity);
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

        return new ProdutoResponseDTO(entity);
    }

    @Override
    public void delete(Long id) {
        if (id == null)
            throw new IllegalArgumentException("Número inválido");

        Produto produto = produtoRepository.findById(id);

        if (produtoRepository.isPersistent(produto)) {
            produtoRepository.delete(produto);
        } else
            throw new NotFoundException("Nenhuma floricultura encontrada.");

    }

    @Override
    public long count() {
        return produtoRepository.count();
    }

    private void validar(ProdutoDTO produtoDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<ProdutoDTO>> violations = validator.validate(produtoDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);

    }

}
