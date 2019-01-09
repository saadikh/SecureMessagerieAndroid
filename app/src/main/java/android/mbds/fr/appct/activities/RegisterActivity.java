package android.mbds.fr.appct.activities;

import android.content.Intent;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.model.Login;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.database.Database;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);

    EditText username;
    EditText password1;
    EditText password2;
    TextView link_login;
    Button valideBtn;

    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.username_box);
        password1 = (EditText)findViewById(R.id.pwd1_box);
        password2 = (EditText)findViewById(R.id.pwd2_box);
        link_login = (TextView)findViewById(R.id.link_login);

        valideBtn = (Button)findViewById(R.id.valideBnt);

        valideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserSQLite();
            }
        });

        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }


    public  void login(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void createUserSQLite(){
        String name = this.username.getText().toString();
        String pwd1 = this.password1.getText().toString();
        String pwd2 = this.password2.getText().toString();

        if(!(pwd1.equals(pwd2))){
            Toast.makeText(getApplicationContext(),
                    R.string.errorPwd, Toast.LENGTH_SHORT).show();
        }
        else{
            //saving for SQLITE
            db= Database.getIstance(getApplicationContext());
            db.addPerson(name, pwd1);

            //synchronize for tokidev server
            createUserTokidev(name, pwd1);
        }
    }

    public void createUserTokidev(String username, String password) {
        Call<ResponseBody> call = userClient.createUser(new Login(username, password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),
                            R.string.saveDb, Toast.LENGTH_SHORT).show();
                    //redirect to login page
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    /*try {
                        Toast.makeText(RegisterActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                } else {
                    Toast.makeText(RegisterActivity.this, "CreateUser error :/\n"+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "CreateUser error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
