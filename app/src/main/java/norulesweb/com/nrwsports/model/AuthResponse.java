package norulesweb.com.nrwsports.model;

import com.google.gson.annotations.Expose;

public class AuthResponse {

    @Expose
    private String token;

    @Expose
    private String message;

    @Expose
    private int status;

    public AuthResponse() {}

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
