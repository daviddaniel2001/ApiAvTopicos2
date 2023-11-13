package br.luahr.topicos1.repository;

import br.luahr.topicos1.model.CupomDesconto;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CupomDescontoRepository implements PanacheRepository<CupomDesconto> {

    public PanacheQuery<CupomDesconto> findByValorDesconto(String valorDesconto) {
        if (valorDesconto == null)
            return null;
        return find("UPPER(valorDesconto) LIKE ?1 ", "%" + valorDesconto.toUpperCase() + "%");
    }
}
