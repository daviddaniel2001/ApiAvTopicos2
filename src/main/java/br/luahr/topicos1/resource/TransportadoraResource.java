package br.luahr.topicos1.resource;

import java.util.List;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.TransportadoraDTO;
import br.luahr.topicos1.dto.TransportadoraResponseDTO;
import br.luahr.topicos1.service.TransportadoraService;
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

@Path("/transportadora")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransportadoraResource {
    
    @Inject
    TransportadoraService transportadoraService;

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<TransportadoraResponseDTO> getAll() {
        return transportadoraService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public TransportadoraResponseDTO findById(@PathParam("id") Long id) {
        return transportadoraService.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert (TransportadoraDTO transportadoraDTO) {
        try {
            TransportadoraResponseDTO transportadora = transportadoraService.create(transportadoraDTO);
            return Response.status(Status.CREATED).entity(transportadora).build(); 
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response update(@PathParam("id") Long id, TransportadoraDTO transportadoraDTO) {
        try {
            transportadoraService.update(id, transportadoraDTO);
            return Response.status(Status.NO_CONTENT).build();
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
}
