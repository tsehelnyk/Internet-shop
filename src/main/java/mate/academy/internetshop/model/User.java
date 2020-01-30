package mate.academy.internetshop.model;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String name;
    private Long id;
    private String login;
    private String password;
    private byte[] salt;
    private String token;
    private Set<Role> userRole = new HashSet<>();

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public Set<Role> getUserRole() {
        return userRole;
    }

    public void setUserRole(Set<Role> userRole) {
        this.userRole = userRole;
    }

    public Set<Role> getRoles() {
        return userRole;
    }

    public void setRoles(Set<Role> role) {
        userRole = role;
    }

    public void addRole(Role role) {
        userRole.add(role);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
