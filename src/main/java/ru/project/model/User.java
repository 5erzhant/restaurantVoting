package ru.project.model;

import java.util.Date;
import java.util.Set;

public class User extends AbstractNamedEntity {
    private String email;
    private String password;
    private Date registered;
    private Set<Role> roles;

    public User(Integer id, String name, String email, String password, Date registered, Set<Role> roles) {
        super(id, name);
        this.email = email;
        this.password = password;
        this.registered = registered;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistered() {
        return registered;
    }

    public void setRegistered(Date registered) {
        this.registered = registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}
