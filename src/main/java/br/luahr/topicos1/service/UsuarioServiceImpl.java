package br.luahr.topicos1.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.luahr.topicos1.model.Usuario;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import jakarta.ws.rs.NotFoundException;
import br.luahr.topicos1.dto.UsuarioDTO;
import br.luahr.topicos1.dto.UsuarioResponseDTO;
import br.luahr.topicos1.model.Endereco;
import br.luahr.topicos1.model.Sexo;
import br.luahr.topicos1.model.Telefone;
import br.luahr.topicos1.repository.UsuarioRepository;
import br.luahr.topicos1.repository.EnderecoRepository;
import br.luahr.topicos1.repository.MunicipioRepository;
import br.luahr.topicos1.repository.TelefoneRepository;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    @Inject
    UsuarioRepository usuarioRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    MunicipioRepository municipioRepository;

    @Inject
    Validator validator;

    @Inject
    HashServiceImpl hashServiceImpl;

    @Override
    public List<UsuarioResponseDTO> getAll(int page, int pageSize) {
        List<Usuario> list = usuarioRepository.findAll().page(page, pageSize).list();
        return list.stream().map(e -> UsuarioResponseDTO.valueOf(e)).collect(Collectors.toList());
    }

    @Override
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findById(id);
        if (usuario == null)
            throw new NotFoundException("Usuario não encontrado.");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO create(UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        //validar(usuarioDTO);

        var entity = new Usuario();
        entity.setNome(usuarioDTO.nome());

        entity.setLogin(usuarioDTO.login());

        entity.setSenha(hashServiceImpl.getHashSenha(usuarioDTO.senha()));
        entity.setCpf(usuarioDTO.cpf());

        Integer idSexo = usuarioDTO.idSexo(); // Obtém o idSexo do DTO
        Sexo sexo = idSexo != null ? Sexo.valueOf(idSexo) : null; // Converte para Sexo, considerando null quando idSexo
                                                                  // for null
        entity.setSexo(sexo); // Seta o sexo no cliente

        Telefone telefone = telefoneRepository.findById(usuarioDTO.idTelefone());
        entity.setTelefone(telefone);

        Endereco endereco = enderecoRepository.findById(usuarioDTO.idEndereco());
        entity.setEndereco(endereco);

        usuarioRepository.persist(entity);

        return UsuarioResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioDTO usuarioDTO) throws ConstraintViolationException {
        validar(usuarioDTO);

        var entity = usuarioRepository.findById(id);
        entity.setNome(usuarioDTO.nome());
        entity.setCpf(usuarioDTO.cpf());

        Integer idSexo = usuarioDTO.idSexo(); // Obtém o idSexo do DTO
        Sexo sexo = idSexo != null ? Sexo.valueOf(idSexo) : null; // Converte para Sexo, considerando null quando idSexo
                                                                  // for null
        entity.setSexo(sexo); // Seta o sexo no cliente

        if (!usuarioDTO.idTelefone().equals(entity.getTelefone().getId())) {
            entity.getTelefone().setId(usuarioDTO.idTelefone());
        }
        if (!usuarioDTO.idEndereco().equals(entity.getEndereco().getId())) {
            entity.getEndereco().setId(usuarioDTO.idEndereco());
        }

        return UsuarioResponseDTO.valueOf(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) throws IllegalArgumentException, NotFoundException {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    @Override
    public UsuarioResponseDTO findByLogin(String login) {
        Usuario usuario = usuarioRepository.findByLogin(login);
        if (usuario == null)
            throw new NotFoundException("Cliente não encontrado");
        return UsuarioResponseDTO.valueOf(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO update(Long id, String nomeImagem) {
        Usuario usuario = usuarioRepository.findById(id);

        usuario.setNomeImagem(nomeImagem);

        return UsuarioResponseDTO.valueOf(usuario);
    }

    // validações
    private void validar(UsuarioDTO usuarioDTO) throws ConstraintViolationException {

        Set<ConstraintViolation<UsuarioDTO>> violations = validator.validate(usuarioDTO);

        if (!violations.isEmpty())
            throw new ConstraintViolationException(violations);
    }

    @Override
    public long count() {
        return usuarioRepository.count();
    }

    @Override
    public long countByNome(String nome) {
        return usuarioRepository.findByNome(nome).count();
    }

        @Override
    public List<UsuarioResponseDTO> findByNome(String nome, int page, int pageSize) {
        List<Usuario> list = usuarioRepository.findByNome(nome).page(page, pageSize).list();
        return list.stream().map(e -> UsuarioResponseDTO.valueOf(e)).collect(Collectors.toList());
    }
}
