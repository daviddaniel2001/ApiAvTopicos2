package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

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

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.TelefoneDTO;
import br.luahr.topicos1.dto.TelefoneResponseDTO;
import br.luahr.topicos1.service.TelefoneService;

@Path("/telefones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TelefoneResource {

    @Inject
    TelefoneService telefoneService;

    private static final Logger LOG = Logger.getLogger(TelefoneResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<TelefoneResponseDTO> getAll(
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSeize") @DefaultValue("10") int pageSize) {
        
        LOG.info("Buscando todos os telefones.");
        LOG.debug("Debug de busca de lista de telefone.");
        return telefoneService.findByNumero(null, page, pageSize);

    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public TelefoneResponseDTO findById(@PathParam("id") Long id) {
        return telefoneService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(TelefoneDTO telefoneDTO) {
        LOG.infof("Inserindo um telefone: %s",telefoneDTO.numero());

        TelefoneResponseDTO telefone = telefoneService.create(telefoneDTO);
        LOG.infof("Telefone (%d) criado com sucesso.", telefone.id());
        return Response.status(Status.CREATED).entity(telefone).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, TelefoneDTO telefoneDTO) {
        LOG.info("Atualiza um telefone.");
        try {
            TelefoneResponseDTO telefone = telefoneService.update(id, telefoneDTO);
            return Response.ok(telefone).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de updat de telefones.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        telefoneService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(){
        return telefoneService.count();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(@PathParam("nome")String numero){
        return telefoneService.count();
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Admin", "User"})
    public List<TelefoneResponseDTO> search(
            @PathParam("nome") String numero,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        
        return telefoneService.findByNumero(numero, page, pageSize);

    }
}
