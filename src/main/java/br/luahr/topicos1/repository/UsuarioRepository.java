package br.luahr.topicos1.repository;

import br.luahr.topicos1.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class UsuarioRepository implements PanacheRepository<Usuario>{
    public PanacheQuery<Usuario> findByNome(String nome){
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1 ", "%" + nome.toUpperCase() + "%");
    }

    public Usuario findByLoginAndSenha(String login, String senha){
        if (login == null || senha == null)
            return null;
            
        return find("login = ?1 AND senha = ?2 ", login, senha).firstResult();
    }

    public Usuario findByLogin(String login){
        if (login == null)
            return null;
            
        return find("login = ?1 ", login).firstResult();
    }
}
