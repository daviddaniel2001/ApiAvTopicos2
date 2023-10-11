package br.luahr.topicos1.resource;

import java.util.List;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.CupomDescontoDTO;
import br.luahr.topicos1.dto.CupomDescontoResponseDTO;
import br.luahr.topicos1.service.CupomDescontoService;
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

@Path("/cupomDesconto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CupomDescontoResource {
    
    @Inject
    CupomDescontoService cupomDescontoService;

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<CupomDescontoResponseDTO> getAll() {
        return cupomDescontoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public CupomDescontoResponseDTO findById(@PathParam("id") Long id) {
        return cupomDescontoService.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert(CupomDescontoDTO cupomDescontoDTO) {
        try {
            CupomDescontoResponseDTO cupomDesconto = cupomDescontoService.create(cupomDescontoDTO);
            return Response.status(Status.CREATED).entity(cupomDesconto).build();
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
    public Response update(@PathParam("Id") Long id, CupomDescontoDTO cupomDescontoDTO) {
        try {
            cupomDescontoService.update(id, cupomDescontoDTO);
            return Response.status(Status.NO_CONTENT).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response delete(@PathParam("id") Long id) {
        cupomDescontoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}
