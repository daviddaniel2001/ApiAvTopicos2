package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.ProdutoDTO;
import br.luahr.topicos1.dto.ProdutoResponseDTO;
import br.luahr.topicos1.service.ProdutoService;
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

@Path("/produto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProdutoResource {
    
    @Inject
    ProdutoService produtoService;

    private static final Logger LOG = Logger.getLogger(EstadoResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<ProdutoResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSeize") @DefaultValue("10") int pageSize){

        LOG.info("Buscando todos os produtos.");
        LOG.debug("ERRO DE DEBUG");
        return produtoService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public ProdutoResponseDTO findById(@PathParam("id") Long id) {
        return produtoService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(ProdutoDTO produtoDTO) {
                LOG.infof("Inserindo um estado: %s",produtoDTO.nome());

        ProdutoResponseDTO produto = produtoService.create(produtoDTO);
        LOG.infof("Produto (%d) criado com sucesso.", produto.id());
        return Response.status(Status.CREATED).entity(produto).build();
    }
    
    @PUT
    @Path("/{id}")
    @RolesAllowed({ "Admin" })
    public Response update(@PathParam("id") Long id, ProdutoDTO produtoDTO) {
        try {
            ProdutoResponseDTO produto = produtoService.update(id, produtoDTO);
            return Response.ok(produto).build();
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

    @GET
    @Path("/search/{nome}/count")
    @RolesAllowed({ "Admin", "User" })
    public long count(@PathParam("nome") String nome){
        return produtoService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    public List<ProdutoResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return produtoService.findByNome(nome, page, pageSize);
        
    }
}
