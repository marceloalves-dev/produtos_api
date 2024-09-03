package marcelo.produtos_api.configuracao;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import marcelo.produtos_api.Usuarios.Usuario;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
            return JWT.create()
                    .withIssuer("produtos_api")
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(tempo())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro na geração do token");
        }
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("123");
            return JWT.require(algorithm)
                    .withIssuer("produtos_api")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException("token invalido");
        }
    }

    private Instant tempo(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
