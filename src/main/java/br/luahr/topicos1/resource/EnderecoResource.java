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
import br.luahr.topicos1.dto.EnderecoDTO;
import br.luahr.topicos1.dto.EnderecoResponseDTO;
import br.luahr.topicos1.service.EnderecoService;

@Path("/enderecos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EnderecoResource {

    @Inject
    EnderecoService enderecoService;

    private static final Logger LOG = Logger.getLogger(EnderecoResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<EnderecoResponseDTO> getAll(
            @QueryParam("page")@DefaultValue("0") int page,
            @QueryParam("pageSize")@DefaultValue("10") int pageSize) {
        
        LOG.info("Buscando todos os enderecos.");
        LOG.debug("ERRO DE DEBUG.");
        return enderecoService.getAll(page, pageSize);
    }
  

    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public EnderecoResponseDTO findById(@PathParam("id") Long id) {
        return enderecoService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(EnderecoDTO enderecoDTO) {
        LOG.infof("Inserindo endereco: %s", enderecoDTO.cep());

        EnderecoResponseDTO endereco = enderecoService.create(enderecoDTO);
        LOG.infof("Endereco (%d) criado com sucesso.", endereco.id());
        return Response.status(Status.CREATED).entity(endereco).build();
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, EnderecoDTO enderecoDTO) {
        LOG.info("Atualiza um endereco.");
        try {
            EnderecoResponseDTO endereco = enderecoService.update(id, enderecoDTO);
            return Response.ok(endereco).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            LOG.debug("Debug de updat de enderecos.");
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        enderecoService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(@PathParam("cep") String cep){
        return enderecoService.countByCep(null);
    }

    @GET
    @Path("/search/{cep}")
    @RolesAllowed({"Admin", "User"})
    public List<EnderecoResponseDTO> search(
            @PathParam("cep")String cep,
            @QueryParam("page")@DefaultValue("0") int page,
            @QueryParam("pageSize")@DefaultValue("10") int pageSize){

        return enderecoService.findByCep(cep, page, pageSize);
    }
}
