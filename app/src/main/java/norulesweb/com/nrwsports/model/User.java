package norulesweb.com.nrwsports.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class User {

    @Expose
    private String username;

    @Expose
    private String firstname;

    @Expose
    private String lastname;

    private String password;

    @Expose
    private String email;

    @Expose
    private List<Object> authorities;

    @Expose
    private boolean enabled;

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Object> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Object> authorities) {
        this.authorities = authorities;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
