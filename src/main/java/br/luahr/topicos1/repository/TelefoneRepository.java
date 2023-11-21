package br.luahr.topicos1.repository;

import jakarta.enterprise.context.ApplicationScoped;

import br.luahr.topicos1.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone>{
    public PanacheQuery<Telefone> findByNumero(String numero){
        if (numero == null)
            return null;
        return find("UPPER(numero) LIKE ?1 ", "%" + numero.toUpperCase() + "%");
    }
}
