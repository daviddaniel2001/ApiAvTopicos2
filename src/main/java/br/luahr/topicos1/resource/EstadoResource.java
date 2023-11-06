package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
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
import br.luahr.topicos1.dto.EstadoDTO;
import br.luahr.topicos1.dto.EstadoResponseDTO;
import br.luahr.topicos1.service.EstadoService;

@Path("/estados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService estadoService;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    public List<EstadoResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSeize") @DefaultValue("10") int pageSize){

        LOG.info("Buscando todos os estados.");
        LOG.debug("ERRO DE DEBUG");
        return estadoService.getAll(page, pageSize);
    }
  
    } 
    @GET
    @Path("/{id}")
    public EstadoResponseDTO findById(@PathParam("id") Long id) {
        return estadoService.findById(id);
    }

    @POST
    //@RolesAllowed({ "Admin" , "User"})
    public Response insert(EstadoDTO estadoDTO) {
        LOG.infof("Inserindo um estado: %s",estadoDTO.nome());

        EstadoResponseDTO estado = estadoService.create(estadoDTO);
        LOG.infof("Estado (%d) criado com sucesso.", estado.id());
        return Response.status(Status.CREATED).entity(estado).build();
    }

    @PUT
    @Path("/{id}")
    /* @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({ "Admin" }) */
    public Response update(@PathParam("id") Long id, EstadoDTO estadoDTO) {
        try {
            EstadoResponseDTO estado = estadoService.update(id, estadoDTO);
            return Response.ok(estado).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    /* @RolesAllowed({ "Admin" }) */
    public Response delete(@PathParam("id") Long id) {
        estadoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/search/{nome}/count")
    /*@RolesAllowed({ "Admin", "User" })*/
    public long count(@PathParam("nome") String nome){
        return estadoService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    public List<EstadoResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return estadoService.findByNome(nome, page, pageSize);
        
    }
}
