package norulesweb.com.nrwsports.model;

import com.google.gson.annotations.Expose;

public class Token {

    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
