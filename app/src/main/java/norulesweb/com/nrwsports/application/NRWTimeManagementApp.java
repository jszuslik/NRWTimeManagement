package norulesweb.com.nrwsports.application;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import norulesweb.com.nrwsports.R;
import norulesweb.com.nrwsports.api.ApiWrapper;
import norulesweb.com.nrwsports.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NRWTimeManagementApp extends Application {

    private static final String TAG = "Application";

    public static final String API_URL = "http://192.168.5.186:9090/api-auth/";

    public static String jwt = "";

    public void onCreate(Bundle savedInstanceState){
        super.onCreate();
    }
    public static void showToast(Context context, String message) {
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }

    public static void showSnackBar(View view, String message, int length) {
        Snackbar.make(view, message, length).show();
    }

    public static void checkLoggedInUser(final ApiWrapper api, final Context context, final SharedPreferences sharedPreferences, final Class activityClass) {
        String defaultToken = context.getResources().getString(R.string.web_token_key_defualt);
        String token = sharedPreferences.getString(context.getString(R.string.web_token_key), defaultToken);

        Log.d(TAG, token);

        Map<String, String> map = new HashMap<>();
        // map.put("Authorization", "Basic YWRtaW46MTIzNDU2");
        map.put("Authorization", token);

        Call<User> call = api.getApi().getUser(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
//                    NRWTimeManagementApp.showToast(getApplicationContext(), response.body().getUsername());
                    Log.d(TAG, response.body().getUsername());
                    startApplication(context, activityClass);
                } else {
//                    NRWTimeManagementApp.showToast(getApplicationContext(), response.message());
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, t.getMessage());
                // NRWTimeManagementApp.showToast(getApplicationContext(), "Login Failed");
            }
        });
    }

    public static void startApplication(Context context, Class activityClass) {
        Intent i = new Intent(context, activityClass);
        context.startActivity(i);
    }

}
