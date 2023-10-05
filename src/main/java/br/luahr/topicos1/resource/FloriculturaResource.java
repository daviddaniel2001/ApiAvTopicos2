package br.luahr.topicos1.resource;

import java.util.List;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.FloriculturaDTO;
import br.luahr.topicos1.dto.FloriculturaResponseDTO;
import br.luahr.topicos1.service.FloriculturaService;
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

@Path("/floricultura")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FloriculturaResource {

    @Inject
    FloriculturaService floriculturaService;

    @GET
    @RolesAllowed({ "Admin", "User" })
    public List<FloriculturaResponseDTO> getAll() {
        return floriculturaService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({ "Admin", "User" })
    public FloriculturaResponseDTO findById(@PathParam("id") Long id) {
        return floriculturaService.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({ "Admin" })
    public Response insert(FloriculturaDTO floriculturaDTO) {
        try {
            FloriculturaResponseDTO floricultura = floriculturaService.create(floriculturaDTO);
            return Response.status(Status.CREATED).entity(floricultura).build();
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
    public Response update(@PathParam("id") Long id, FloriculturaDTO floriculturaDTO) {
        try {
            floriculturaService.update(id, floriculturaDTO);
            return Response.status(Status.NO_CONTENT).build();
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
}
