package br.luahr.topicos1.resource;

import java.util.List;

import org.jboss.logging.Logger;

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

import br.luahr.topicos1.application.Result;
import br.luahr.topicos1.dto.FornecedorDTO;
import br.luahr.topicos1.dto.FornecedorResponseDTO;
import br.luahr.topicos1.service.FornecedorService;

@Path("/fornecedores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class FornecedorResource {
    @Inject
    FornecedorService fornecedorService;

    private static final Logger LOG = Logger.getLogger(FornecedorResource.class);

    @GET
    @RolesAllowed({"Admin", "User"})
    public List<FornecedorResponseDTO> getAll(

            @QueryParam("page") @DefaultValue("o") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize){
        
        LOG.info("Buscando por todas as floriculturas");
        LOG.debug("ERRO DE DEBUG");
        return fornecedorService.getAll(page, pageSize);

    }


    @GET
    @Path("/{id}")
    @RolesAllowed({"Admin", "User"})
    public FornecedorResponseDTO findById(@PathParam("id") Long id) {
        return fornecedorService.findById(id);
    }

    @POST
    @RolesAllowed({"Admin"})
    public Response insert(FornecedorDTO fornecedorDTO) {
        LOG.infof("Inserindo um fornecedor: %s", fornecedorDTO.nome());

        FornecedorResponseDTO fornecedor = fornecedorService.create(fornecedorDTO);
        LOG.infof("Fornecedor (%d) criado com sucesso.", fornecedor.id());
        return Response.status(Status.CREATED).entity(fornecedor).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"Admin"})
    public Response update(@PathParam("id") Long id, FornecedorDTO fornecedorDTO) {
        LOG.info("Atualiza um fornecedor.");
        try {
            FornecedorResponseDTO fornecedor = fornecedorService.update(id, fornecedorDTO);
            return Response.ok(fornecedor).build();
        } catch(ConstraintViolationException e) {
            Result result = new Result(e.getConstraintViolations());
            return Response.status(Status.NOT_FOUND).entity(result).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed({"Admin"})
    public Response delete(@PathParam("id") Long id) {
        fornecedorService.delete(id);
        return Response.status(Status.NO_CONTENT).build();
    }

    @GET
    @Path("/count")
    @RolesAllowed({"Admin", "User"})
    public long count(){
        return fornecedorService.count();
    }

    @GET
    @Path("/search/{nome}/count")
    public long count(@PathParam("nome") String nome) {
        return fornecedorService.countByNome(nome);
    }

    @GET
    @Path("/search/{nome}")
    @RolesAllowed({"Admin", "User"})
    public List<FornecedorResponseDTO> search(
            @PathParam("nome") String nome,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("pageSize") @DefaultValue("10") int pageSize) {
        return fornecedorService.findByNome(nome, page, pageSize);
    }
}
