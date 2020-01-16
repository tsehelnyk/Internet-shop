package mate.academy.internetshop.model;

public class Role {
    private final Long id;
    private RoleName roleName;

    public Role(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public enum RoleName {
        ADMIN, USER;
    }
}
