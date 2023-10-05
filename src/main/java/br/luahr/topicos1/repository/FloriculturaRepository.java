package br.luahr.topicos1.repository;

import br.luahr.topicos1.model.Floricultura;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FloriculturaRepository implements PanacheRepository<Floricultura> {
    
    public FloriculturaRepository findByNome(String nome) {
         if(nome == null)
            return null;
        return findByNome(nome);
    }
}
