package android.mbds.fr.appct.activities;

import android.content.Intent;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.model.Login;
import android.mbds.fr.appct.api.model.User;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.models.Person;
import android.mbds.fr.appct.utils.CryptoManager;
import android.mbds.fr.appct.utils.PreferencesManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    private UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);

    EditText usernameBox, passwordBox;
    Button loginButton;
    TextView linkRegister;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameBox = (EditText)findViewById(R.id.username_box);
        passwordBox = (EditText)findViewById(R.id.pwd_box);
        loginButton = (Button)findViewById(R.id.valideBnt);
        linkRegister = (TextView)findViewById(R.id.link_register);

        //autoremplissage
        usernameBox.setText(PreferencesManager.getInstance(getApplicationContext()).loadUsername());
        passwordBox.setText(PreferencesManager.getInstance(getApplicationContext()).loadPwd());

        //create PK_RSA and save by sharedpreferences
        PublicKey publicKey = CryptoManager.getInstance().generateMyRsaKey();
        PreferencesManager.getInstance(getApplicationContext()).saveRsaPublic(publicKey.toString());
        Log.i("PK PK PK", publicKey.toString());
        //Toast.makeText(this, "PK userTest : "+publicKey.toString(), Toast.LENGTH_SHORT).show();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                login(usernameBox.getText().toString().trim(), passwordBox.getText().toString().trim());
            }
        });

        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }


        private void login(String username, String password) {
            Call<User> call = userClient.login(new Login(username, password));
            call.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(LoginActivity.this, response.body().getAccess_token(), Toast.LENGTH_SHORT).show();
                        String token = response.body().getAccess_token();
                        String refreshToken = response.body().getRefresh_token();
                        PreferencesManager.getInstance(getApplicationContext()).saveAccessToken(token);
                        PreferencesManager.getInstance(getApplicationContext()).saveRefreshToken(refreshToken);

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Login error :/\n" + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.i("Login error", "" + t);
                    Toast.makeText(LoginActivity.this, "Login error :/\n" + t, Toast.LENGTH_SHORT).show();
                }
            });
        }

    public void register(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

}
