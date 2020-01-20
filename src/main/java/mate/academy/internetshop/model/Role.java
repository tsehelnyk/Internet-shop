package mate.academy.internetshop.model;

public class Role {
    private Long id;
    private RoleName roleName;

    public Role(Long id) {
        this.id = id;
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
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

    @Override
    public String toString() {
        return roleName.toString();
    }

    public enum RoleName {
        ADMIN, USER;
    }
}
