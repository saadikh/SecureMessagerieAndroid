package android.mbds.fr.appct.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.TalkActivity;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.fragments.ContactListFragment;
import android.mbds.fr.appct.fragments.MessagerieFragment;
import android.mbds.fr.appct.utils.PreferencesManager;
import android.mbds.fr.appct.utils.iCallable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements iCallable{
    private UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);

    // addontouchlistener -> pour gerer le click sur les items
    FrameLayout flc;
    FrameLayout fm;
    FloatingActionButton btnRefresh;
    FloatingActionButton btnTobaobab;
    ContactListFragment clf = new ContactListFragment();;
    MessagerieFragment mf = new MessagerieFragment();;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flc = (FrameLayout)findViewById(R.id.fragment_contact_list);
        fm = (FrameLayout)findViewById(R.id.fragment_message_item);
        btnRefresh = (FloatingActionButton)findViewById(R.id.btn_refresh_cnt);
        btnTobaobab = (FloatingActionButton)findViewById(R.id.btn_baobab);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });

        btnTobaobab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toBaobabApi();
            }
        });


        FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();


        if(!(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)){
            ft.replace(flc.getId(), clf);
            ft.replace(fm.getId(), mf);
        }
        else{
            String val;
            Intent i = this.getIntent();
            val = i.getStringExtra("username");

            if(val!=null){
                mf.setRecipientMessage(val);
                ft.replace(fm.getId(), mf);
            }
            else {
                ft.replace(flc.getId(), clf);
                String pbRsa = PreferencesManager.getInstance(getApplicationContext()).loadRsaPublic();
                //String userTest = PreferencesManager.getInstance(this).loadKPContact("user").trim();
                //String copyRest = PreferencesManager.getInstance(this).decryptString()
                //String token = PreferencesManager.getInstance(this).loadAccessToken().trim();
                Toast.makeText(this, "PK userTest : "+pbRsa, Toast.LENGTH_SHORT).show();
            }
        }

        ft.commit();
    }

    @Override
    public void sendContact(String username) {
        mf.setRecipientMessage(username);
    }


    public void toBaobabApi(){
        Intent intent = new Intent(this, TalkActivity.class);
        startActivity(intent);
    }

    public void validate() {
        String token = PreferencesManager.getInstance(getApplicationContext()).refreshConnexion().trim();

        Call<ResponseBody> call = userClient.validate("Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Validate error :/\n", Toast.LENGTH_SHORT).show();
                    //redirect to login activity
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Validate error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}


