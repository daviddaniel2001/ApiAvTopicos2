package br.luahr.topicos1.repository;

import br.luahr.topicos1.model.CupomDesconto;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CupomDescontoRepository implements PanacheRepository<CupomDesconto> {
    
    public CupomDescontoRepository findByCodigo(String codigo) {
        if(codigo == null)
            return null;
        return findByCodigo(codigo);
    }
}
