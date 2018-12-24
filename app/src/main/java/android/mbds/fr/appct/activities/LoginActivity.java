package android.mbds.fr.appct.activities;

import android.content.Intent;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.Person;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {



    EditText username;
    EditText password;
    Button valideBtn;
    TextView linkRegister;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username_box);
        password = (EditText)findViewById(R.id.pwd_box);
        valideBtn = (Button)findViewById(R.id.valideBnt);
        linkRegister = (TextView)findViewById(R.id.link_register);

        valideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public boolean isConnected(){
        db= Database.getIstance(getApplicationContext());
        ArrayList<Person> result = db.readPerson();

        String name = this.username.getText().toString();
        String pwd = this.password.getText().toString();

        for(Person person: result){
            if(person.getUsername().equals(name) && person.getPassword().equals(pwd)){
                return true;
            }
        }
        return false;

    }

    public void register(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(){ //
        boolean result = isConnected();
        if(result){
            //valideBtn.setBackgroundColor(Color.GREEN);
            Intent intent = new Intent(this, ContactActivity.class);
            startActivity(intent);
        }
        else{
            //valideBtn.setBackgroundColor(Color.RED);
            Toast.makeText(getApplicationContext(),
                    R.string.login_failed, Toast.LENGTH_SHORT).show();
        }
    }
}
