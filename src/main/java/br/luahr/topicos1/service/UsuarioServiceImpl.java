package br.luahr.topicos1.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import br.luahr.topicos1.dto.TelefoneDTO;
import br.luahr.topicos1.dto.UsuarioDTO;
import br.luahr.topicos1.dto.UsuarioResponseDTO;
import br.luahr.topicos1.model.Telefone;
import br.luahr.topicos1.model.Usuario;
import br.luahr.topicos1.repository.UsuarioRepository;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResponseDTO insert(UsuarioDTO usuarioDto) {

        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioDto.nome());
        novoUsuario.setLogin(usuarioDto.login());
        novoUsuario.setSenha(usuarioDto.senha());

        if(usuarioDto.listaTelefone() != null &&
                        !usuarioDto.listaTelefone().isEmpty()){
                    novoUsuario.setListaTelefone(new ArrayList<Telefone>());
                    novoUsuario.setListaTelefone(new ArrayList<Telefone>());
                    for (TelefoneDTO tel : usuarioDto.listaTelefone()) {
                        Telefone telefone = new Telefone();
                        telefone.setCodigoArea(tel.codigoArea());
                        telefone.setNumero(tel.numero());
                        novoUsuario.getListaTelefone().add(telefone);
       
            }
        }

        usuarioRepository.persist(novoUsuario);

        return UsuarioResponseDTO.valueOf(novoUsuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(UsuarioDTO usuarioDTO, Long id) {
        return null;
    }

    @Override
    @Transactional
    public void delete(Long id) {
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public List<UsuarioResponseDTO> findByNome(String nome) {
        return null;
    }

    @Override
    public List<UsuarioResponseDTO> findByAll() {
        return usuarioRepository.listAll().stream()
                .map(e -> UsuarioResponseDTO.valueOf(e)).toList();
    }

    @Override
    public UsuarioResponseDTO findByLoginSenha(String login, String senha) {
        Usuario usuario = usuarioRepository.findByLoginAndSenha(login, senha);
        return UsuarioResponseDTO.valueOf(usuario);
    }
}
