package br.luahr.topicos1.resource;

import java.util.List;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.ProdutoDTO;
import br.luahr.topicos1.dto.ProdutoResponseDTO;
import br.luahr.topicos1.service.ProdutoService;
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

@Path("/produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    
    @Inject
    ProdutoService produtoService;

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<ProdutoResponseDTO> getAll() {
        return produtoService.getAll();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public ProdutoResponseDTO findById(@PathParam("id") Long id) {
        return produtoService.findById(id);
    }

    @POST
    @Transactional
    @RolesAllowed({"Admin"})
    public Response insert(ProdutoDTO produtoDTO) {
        try {
            ProdutoResponseDTO produto = produtoService.create(produtoDTO);
            return Response.status(Status.CREATED).entity(produto).build();
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
    public Response update(@PathParam("id") Long id, ProdutoDTO produtoDTO) {
        try {
            produtoService.update(id, produtoDTO);
            return Response.status(Status.NO_CONTENT).build();
        } catch (ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public Response delete(@PathParam("id") Long id) {
        produtoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }
}
