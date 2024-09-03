package marcelo.produtos_api.Usuarios;

public enum Roles {

    ADMIN("admin"),
    USER("user");

    private String Role;

    Roles(String role) {
        Role = role;
    }

    public String getRole() {
        return Role;
    }
}
