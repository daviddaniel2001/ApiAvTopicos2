package br.luahr.topicos1.repository;

import java.util.List;

import br.luahr.topicos1.model.Produto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ProdutoRepository implements PanacheRepository<Produto> {
    public List<Produto> findByNome(String nome) {
        if(nome == null)
            return null;
        return findByNome(nome);
    }
}
