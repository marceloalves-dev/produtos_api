package marcelo.produtos_api.Usuarios;

public record RegistroDTO(
        String login,
        String senha,
        Roles role
) {
}
