package norulesweb.com.nrwsports.api;

import java.util.Map;

import norulesweb.com.nrwsports.application.NRWTimeManagementApp;
import norulesweb.com.nrwsports.model.AuthResponse;
import norulesweb.com.nrwsports.model.User;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;

public class ApiWrapper {

    public ApiWrapper() {}

    private static AuthAppApiInterface api;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                .baseUrl(NRWTimeManagementApp.API_URL)
                .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    public static Retrofit retrofit() {
        return retrofit;
    }

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

    public AuthAppApiInterface getApi() {
        if (api == null) {
            api = ApiWrapper.createService(AuthAppApiInterface.class);
        }
        return api;
    }

    public interface AuthAppApiInterface {

        @GET("auth")
        Call<AuthResponse> login(@HeaderMap Map<String, String> headers);

        @GET("user")
        Call<User> getUser(@HeaderMap Map<String, String> headers);

    }

}
