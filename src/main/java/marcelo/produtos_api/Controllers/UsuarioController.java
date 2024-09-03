package marcelo.produtos_api.Controllers;


import marcelo.produtos_api.Usuarios.DadosAutenticacao;
import marcelo.produtos_api.Usuarios.RegistroDTO;
import marcelo.produtos_api.Usuarios.Usuario;
import marcelo.produtos_api.Usuarios.UsuarioRepository;
import marcelo.produtos_api.configuracao.DadosTokenJWT;
import marcelo.produtos_api.configuracao.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/login")
public class UsuarioController {

    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/logar")
    public ResponseEntity<?> efetuarLogin(@RequestBody DadosAutenticacao dadosAutenticacao) {
        var token = new UsernamePasswordAuthenticationToken(dadosAutenticacao.login(), dadosAutenticacao.senha());

        var autentication = manager.authenticate(token);

        var tokenJWT = tokenService.gerarToken((Usuario) autentication.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));

    }

    @PostMapping("/registrar")
    public ResponseEntity<?> criarConta(@RequestBody RegistroDTO data) {
        if (repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encriptar = new BCryptPasswordEncoder().encode(data.senha());

        Usuario usuario = new Usuario(data.login(), encriptar, data.role());

        repository.save(usuario);
        return ResponseEntity.ok().build();
    }
}
