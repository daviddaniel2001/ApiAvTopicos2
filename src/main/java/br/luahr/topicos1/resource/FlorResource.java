package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
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
import jakarta.ws.rs.DefaultValue;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.FlorDTO;
import br.luahr.topicos1.dto.FlorResponseDTO;
import br.luahr.topicos1.repository.FlorRepository;
import br.luahr.topicos1.service.FileService;
import br.luahr.topicos1.service.FlorService;

@Path("/flores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FlorResource {
    @Inject
    FlorService florService;

    @Inject
    FileService fileService;

    @Inject
    FlorRepository florRepository;

    private static final Logger LOG = Logger.getLogger(FlorResource.class);

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<FlorResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("o") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        LOG.info("Buscando por todas as flores");
        LOG.debug("ERRO DE DEBUG");
        return florService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public FlorResponseDTO findById(@PathParam("id") Long id) {
        return florService.findById(id);
    }

    @POST
    @RolesAllowed({ "Admin" })
    public Response insert(FlorDTO florDTO) {
        LOG.infof("Inserindo uma flor: %s", florDTO.nome());

        FlorResponseDTO flor = florService.create(florDTO);
        LOG.infof("Flor (%d) criada com sucesso.", flor.id());
        return Response.status(Status.CREATED).entity(flor).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, FlorDTO florDTO) {
        try {
            FlorResponseDTO flor = florService.update(id, florDTO);
            return Response.ok(flor).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public Response delete(@PathParam("id") Long id) {
        florService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    public long count() {
        return florService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome) {
        return florService.countByNome(nome);
    }

    public List<FlorResponseDTO> search (
        @PathParam("nome") String nome,
        @QueryParam("page") @DefaultValue("0") int page,
        @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
    return florService.findByNome(nome, page, pageSize);
        }

}

