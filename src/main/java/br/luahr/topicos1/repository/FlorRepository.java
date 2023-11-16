package br.luahr.topicos1.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.luahr.topicos1.model.Flor;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class FlorRepository implements PanacheRepository<Flor>{
    public PanacheQuery<Flor> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%");
    }
}
