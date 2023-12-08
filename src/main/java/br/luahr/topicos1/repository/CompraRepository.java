package br.luahr.topicos1.repository;

import br.luahr.topicos1.model.Compra;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {

    public PanacheQuery<Compra> findByItemFlor(Long itemFlor) {
        if(itemFlor == null)
            return null;
        return find("UPPER(itemProduto) LIKE ?1 ");
    }
}
