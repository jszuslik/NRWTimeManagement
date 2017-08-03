package norulesweb.com.nrwsports;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import norulesweb.com.nrwsports.api.ApiWrapper;
import norulesweb.com.nrwsports.application.NRWTimeManagementApp;
import norulesweb.com.nrwsports.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabsActivity extends AppCompatActivity {

    private ApiWrapper api;

    private static final String TAG = "TabsActivity";

    TabHost mTabHost;

    TabHost.TabSpec mTabHostSpec;

    SharedPreferences sharedPreferences;

    private View mProgressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        mProgressView = findViewById(R.id.tabs_progress);
        if(api == null) {
            api = new ApiWrapper();
        }
        sharedPreferences = TabsActivity.this.getSharedPreferences(getString(R.string.web_token_key), Context.MODE_PRIVATE);
        String defaultToken = getResources().getString(R.string.web_token_key_defualt);
        String token = sharedPreferences.getString(getString(R.string.web_token_key), defaultToken);
        Log.d(TAG, token);
        NRWTimeManagementApp.showToast(TabsActivity.this, token);

        mTabHost = (TabHost) findViewById(R.id.tabhost);
        mTabHost.setup();

        //Tab 1
        mTabHostSpec = mTabHost.newTabSpec("Play by Play");
        mTabHostSpec.setContent(R.id.tab1);
        mTabHostSpec.setIndicator("Play by Play");
        mTabHost.addTab(mTabHostSpec);

        //Tab 2
        mTabHostSpec = mTabHost.newTabSpec("Teammates");
        mTabHostSpec.setContent(R.id.tab2);
        mTabHostSpec.setIndicator("Teammates");
        mTabHost.addTab(mTabHostSpec);

        //Tab 3
        mTabHostSpec = mTabHost.newTabSpec("Score");
        mTabHostSpec.setContent(R.id.tab3);
        mTabHostSpec.setIndicator("Score");
        mTabHost.addTab(mTabHostSpec);

        //Tab 4
        mTabHostSpec = mTabHost.newTabSpec("My Stats");
        mTabHostSpec.setContent(R.id.tab4);
        mTabHostSpec.setIndicator("My Stats");
        mTabHost.addTab(mTabHostSpec);

        //Tab 5
        mTabHostSpec = mTabHost.newTabSpec("Bio");
        mTabHostSpec.setContent(R.id.tab5);
        mTabHostSpec.setIndicator("Bios");
        mTabHost.addTab(mTabHostSpec);

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                Log.d(TAG, tabId);
                switch (tabId) {
                    case "Bio":
                        showProgress(true);
                        setBio();
                        break;
                }
            }
        });

    }

    private void setBio() {

        String defaultToken = getResources().getString(R.string.web_token_key_defualt);
        String token = sharedPreferences.getString(getString(R.string.web_token_key), defaultToken);

        Log.d(TAG, token);

        Map<String, String> map = new HashMap<>();
        map.put("Authorization", token);

        Call<User> call = api.getApi().getUser(map);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
//                    NRWTimeManagementApp.showToast(getApplicationContext(), response.body().getUsername());
                    TextView tv1 = (TextView) findViewById(R.id.bio_view_text_1);
                    TextView tv2 = (TextView) findViewById(R.id.bio_view_text_2);
                    TextView tv3 = (TextView) findViewById(R.id.bio_view_text_3);
                    ImageView iv = (ImageView) findViewById(R.id.bio_image);
                    Log.d(TAG, response.body().getUsername());
                    tv1.setText("Username: " + response.body().getUsername());
                    tv2.setText("Name: " + response.body().getFirstname() + " " + response.body().getLastname());
                    tv3.setText("Email: " + response.body().getEmail());
                    Picasso.with(TabsActivity.this).load("https://joshuaszuslik.us/wp-content/uploads/2016/03/josh-e1457461711638.jpg").fit().into(iv);
                    showProgress(false);
                } else {
                    Log.d(TAG, response.message());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
         // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            // action with ID action_settings was selected
            case R.id.action_logout:
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(getString(R.string.web_token_key));
                editor.commit();
                Toast.makeText(this, "User Logged Out", Toast.LENGTH_SHORT)
                        .show();
                showProgress(true);
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                showProgress(false);
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mTabHost.setVisibility(show ? View.GONE : View.VISIBLE);
            mTabHost.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mTabHost.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mTabHost.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
