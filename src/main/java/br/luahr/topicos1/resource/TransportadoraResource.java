package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.TransportadoraDTO;
import br.luahr.topicos1.dto.TransportadoraResponseDTO;
import br.luahr.topicos1.service.TransportadoraService;
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

@Path("/transportadora")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransportadoraResource {
    
    @Inject
    TransportadoraService transportadoraService;

    private static final Logger LOG = Logger.getLogger(TransportadoraResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<TransportadoraResponseDTO> getAll(
            @QueryParam("page")@DefaultValue("0") int page,
            @QueryParam("pageSize")@DefaultValue("10") int pageSize) {

        LOG.info("Buscando todas as transportadoras .");
        LOG.debug("ERRO DE DEBUG");
        return transportadoraService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public TransportadoraResponseDTO findById(@PathParam("id") Long id) {
        return transportadoraService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert (TransportadoraDTO transportadoraDTO) {
        LOG.infof("Inserindo uma transportadora: %s",transportadoraDTO.nome());

        TransportadoraResponseDTO transporadora = transportadoraService.create(transportadoraDTO);
        LOG.infof("Transportadora (%d) criada com sucesso.", transporadora.id());
        return Response.status(Status.CREATED).entity(transporadora).build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, TransportadoraDTO transportadoraDTO) {
        try {
            TransportadoraResponseDTO transportadora = transportadoraService.update(id, transportadoraDTO);
            return Response.ok(transportadora).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response delele(@PathParam("id") Long id) {
        transportadoraService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/search/{nome}/count")
    @RolesAllowed({ "Admin", "User" })
    public long count(@PathParam("nome") String nome){
        return transportadoraService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    public List<TransportadoraResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return transportadoraService.findByNome(nome, page, pageSize);
        
    }

}
