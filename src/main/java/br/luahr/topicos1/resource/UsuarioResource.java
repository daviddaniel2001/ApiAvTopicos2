package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.UsuarioDTO;
import br.luahr.topicos1.dto.UsuarioResponseDTO;
import br.luahr.topicos1.service.UsuarioService;

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<UsuarioResponseDTO> getAll() {
        LOG.info("Buscando todos os clientes.");
        LOG.debug("Debug de busca de lista de clientes.");
        return usuarioService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public UsuarioResponseDTO findById(@PathParam("id") Long id) {
        LOG.info("Buscando um cliente por ID.");
        LOG.debug("Debug de busca de ID de clientes.");
        return usuarioService.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert(UsuarioDTO usuarioDTO) {
        LOG.info("Inserindo um cliente.");
        try {
            UsuarioResponseDTO cliente = usuarioService.create(usuarioDTO);
            return Response.status(Status.CREATED).entity(cliente).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de inserção de clientes.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Long id, UsuarioDTO usuarioDTO) {
        LOG.info("Atualiza um cliente.");
        try {
            UsuarioResponseDTO cliente = usuarioService.update(id, usuarioDTO);
            return Response.status(Status.NO_CONTENT).entity(cliente).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de updat de clientes.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        LOG.info("deleta um cliente.");
        usuarioService.delete(id);
        LOG.debug("Debug de deletar clientes.");
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(){
        LOG.info("Conta clientes.");
        return usuarioService.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Admin", "User"})
    public List<UsuarioResponseDTO> search(@PathParam("nome") String nome){
        LOG.info("Busca nome de clientes.");
        return usuarioService.findByNome(nome);
    }
    
}
