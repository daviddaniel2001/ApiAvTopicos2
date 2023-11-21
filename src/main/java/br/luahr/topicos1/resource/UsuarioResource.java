package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
    public List<UsuarioResponseDTO> getAll(
            @QueryParam("page")@DefaultValue("0") int page,
            @QueryParam("pageSize")@DefaultValue("10") int pageSize) {
        
        LOG.info("Buscando todos os usuarios.");
        LOG.debug("ERRO DE DEBUG");
        return usuarioService.getAll(page, pageSize);
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
    @RolesAllowed({"Admin"})
    public Response insert(UsuarioDTO usuarioDTO) {
        LOG.infof("Inserindo um usuario: %s",usuarioDTO.nome());

        UsuarioResponseDTO usuario = usuarioService.create(usuarioDTO);
        LOG.infof("Usuario (%d) criado com sucesso.", usuario.id());
        return Response.status(Status.CREATED).entity(usuario).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, UsuarioDTO usuarioDTO) {
        LOG.info("Atualiza um cliente.");
        try {
            UsuarioResponseDTO usuario = usuarioService.update(id, usuarioDTO);
            return Response.status(Status.NO_CONTENT).entity(usuario).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de update de clientes.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        usuarioService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(@PathParam("nome") String nome){
        LOG.info("Conta clientes.");
        return usuarioService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Admin", "User"})
    public List<UsuarioResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize){

        return usuarioService.findByNome(nome, page, pageSize);
    }    
}
