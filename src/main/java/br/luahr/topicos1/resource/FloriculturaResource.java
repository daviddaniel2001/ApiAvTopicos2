package br.luahr.topicos1.resource;

import java.util.List;
import org.jboss.logging.Logger;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.FloriculturaDTO;
import br.luahr.topicos1.dto.FloriculturaResponseDTO;
import br.luahr.topicos1.service.FloriculturaService;
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

@Path("/floricultura")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FloriculturaResource {

    @Inject
    FloriculturaService floriculturaService;

    private static final Logger LOG = Logger.getLogger(FloriculturaResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<FloriculturaResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("o") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        LOG.info("Buscando por todas as floriculturas");
        LOG.debug("ERRO DE DEBUG");
        return floriculturaService.getAll(page, pageSize);
    }
    
    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public FloriculturaResponseDTO findById(@PathParam("id") Long id) {
        return floriculturaService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(FloriculturaDTO floriculturaDTO) {
        LOG.infof("Inserindo uma floricultura: %s", floriculturaDTO.nome());

        FloriculturaResponseDTO floricultura = floriculturaService.create(floriculturaDTO);
        LOG.infof("Floricultura (%d) criada com sucesso.", floricultura.id());
        return Response.status(Status.CREATED).entity(floricultura).build();


    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FloriculturaDTO floriculturaDTO) {
        try {
            FloriculturaResponseDTO floricultura = floriculturaService.update(id, floriculturaDTO);
            return Response.ok(floricultura).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public Response delete(@PathParam("id") Long id) {
        floriculturaService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return floriculturaService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome) {
        return floriculturaService.countByNome(nome);
    }
    
    public List<FloriculturaResponseDTO> search (
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return floriculturaService.findByNome(nome, page, pageSize);
            }
}
