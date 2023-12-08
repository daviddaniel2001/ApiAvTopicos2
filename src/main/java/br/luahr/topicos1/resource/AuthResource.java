package br.luahr.topicos1.resource;


import br.luahr.topicos1.dto.AuthUsuarioDTO;
import br.luahr.topicos1.dto.UsuarioResponseDTO;
import br.luahr.topicos1.service.UsuarioService;
import br.luahr.topicos1.service.HashService;
import br.luahr.topicos1.service.TokenJwtService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    HashService hashService;

    @Inject
    UsuarioService usuarioService;

    @Inject
    TokenJwtService tokenService;

    @POST
    public Response login(AuthUsuarioDTO authDTO) {
        String hash = hashService.getHashSenha(authDTO.senha());

        UsuarioResponseDTO usuario = usuarioService.findByLoginSenha(authDTO.login(), hash);

        if (usuario == null) {
            return Response.status(Status.NOT_FOUND)
                .entity("Usuario não encontrado").build();
        } 
        return Response.ok(usuario)
            .header("Authorization", tokenService.generateJwt(usuario))
            .build();
        
    }
  
}