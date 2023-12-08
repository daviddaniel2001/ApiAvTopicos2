package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.CompraDTO;
import br.luahr.topicos1.dto.CompraResponseDTO;
import br.luahr.topicos1.service.CompraService;
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

@Path("/compras")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    private static final Logger LOG = Logger.getLogger(UsuarioResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<CompraResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        LOG.info("Buscando todas a compras.");
        LOG.debug("ERRO DE DEBUG");
        return compraService.getAll(page, pageSize);
        
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public CompraResponseDTO findById(@PathParam("id") Long id) {
        return compraService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(CompraDTO compraDTO) {
        LOG.infof("Inserindo uma compra: %s", compraDTO.itemFlor());

        CompraResponseDTO compra = compraService.create(compraDTO);
        LOG.infof("Compra (%id) feita com sucesso.", compra.id());
        return Response.status(Status.CREATED).entity(compra).build();

    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, CompraDTO compraDTO) {
        try {
            CompraResponseDTO compra = compraService.update(id, compraDTO);
            return Response.ok(compra).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de updat de clientes.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        compraService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(){
        return compraService.count();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(@PathParam("itemFlor") Long itemFlor){
        return compraService.countByItemFlor(itemFlor);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Admin", "User"})
    public List<CompraResponseDTO> search(
            @PathParam("itemProduto") Long itemProduto,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize){
        return compraService.findByItemFlor(itemProduto, page, pageSize);

    }
    
}
