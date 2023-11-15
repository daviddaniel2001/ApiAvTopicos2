package br.luahr.topicos1.resource;

import java.util.List;
import org.jboss.logging.Logger;

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

@Path("/cupomDesconto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CupomDescontoResource {
    
    @Inject
    CupomDescontoService cupomDescontoService;

    private static final Logger LOG = Logger.getLogger(CupomDescontoResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<CupomDescontoResponseDTO> getAll(
            @QueryParam("page")@DefaultValue("0") int page,
            @QueryParam("pageSize")@DefaultValue("10") int pageSize) {

        LOG.info("Buscando todos os cupons.");
        LOG.debug("ERRO DE DEBUG.");
        return cupomDescontoService.getAll(page, pageSize);
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public CupomDescontoResponseDTO findById(@PathParam("id") Long id) {
        return cupomDescontoService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(CupomDescontoDTO cupomDescontoDTO) {
        LOG.infof("Inserindo um cupom: %s", cupomDescontoDTO.valorDesconto());
        
        CupomDescontoResponseDTO cupomDesconto = cupomDescontoService.create(cupomDescontoDTO);
        LOG.infof("Cupom (%d) criado com sucesso.", cupomDesconto.id());
        return Response.status(Status.CREATED).entity(cupomDesconto).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("Id") Long id, CupomDescontoDTO cupomDescontoDTO) {
        try {
            CupomDescontoResponseDTO cupomDesconto = cupomDescontoService.update(id, cupomDescontoDTO);
            return Response.ok(cupomDesconto).build();
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

    @GET
    @Path("/search/{nome}/count")
    @RolesAllowed({ "Admin", "User" })
    public long count(@PathParam("valorDesconto") String valorDesconto){
        return cupomDescontoService.countByValorDesconto(valorDesconto);
    }

    @GET
    @Path("/search/{nome}")
    public List<CupomDescontoResponseDTO> search(
            @PathParam("valorDesconto") String valorDesconto,
            @QueryParam("page") @DefaultValue("0") int page, 
            @QueryParam("pageSize") @DefaultValue("10") int pageSize){

        return cupomDescontoService.findByValorDesconto(valorDesconto, page, pageSize);

    }
}
