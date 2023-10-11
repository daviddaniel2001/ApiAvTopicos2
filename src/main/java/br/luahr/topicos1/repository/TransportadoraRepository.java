package br.luahr.topicos1.repository;

import br.luahr.topicos1.model.Transportadora;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TransportadoraRepository implements PanacheRepository<Transportadora> {
    
    public TransportadoraRepository findByNome(String nome) {
        if(nome == null)
            return null;
        return findByNome(nome);
    }
}
