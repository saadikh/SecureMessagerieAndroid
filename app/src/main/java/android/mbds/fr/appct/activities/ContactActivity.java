package android.mbds.fr.appct.activities;

import android.content.Intent;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.model.Login;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.Contact;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactActivity extends AppCompatActivity {
    private UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);

    EditText etUsername;
    Button btnSave;
    private Database db;

    boolean isSaved = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        etUsername = (EditText)findViewById(R.id.ctUsernameBox);
        btnSave = (Button)findViewById(R.id.btnNewContact);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContactSQLite();
            }
        });

    }


    /*public boolean isAddContact(){
        db= Database.getIstance(getApplicationContext());// revoir
        List<Contact> result = db.readContact();

        if(result.size() != 0 ) {
            return true;
        }
        else {
            return false;
        }
    }*/

    public void addContactSQLite(){
        db= Database.getIstance(this);
        db.addContact(etUsername.getText().toString());

        //ct(chen_datao)[|]PONG[|]android.security.keystore.AndroidKeyStoreRSAPublicKe2d4b318c

        //for adding on tokidev

        //redirect to login page
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        /*FrameLayout fl = (FrameLayout)findViewById(R.id.fragment_contact_list);

        ContactListFragment clf = new ContactListFragment();
        FragmentTransaction ft  = getSupportFragmentManager().beginTransaction();

        if(isAdded()){
            Toast.makeText(getApplicationContext(),
                    R.string.saveDb, Toast.LENGTH_SHORT).show();
            ft.replace(fl.getId(), clf);
            ft.commit();
        }
        else{
            Toast.makeText(getApplicationContext(),
                    R.string.noSaved, Toast.LENGTH_SHORT).show();
        }*/

    }

    public boolean isAdded()
    {
        db= Database.getIstance(getApplicationContext());
        ArrayList<Contact> result = db.readContact();

        Contact newContact = new Contact(etUsername.getText().toString());
        for(Contact contact: result){
            if(contact.getUsername() == newContact.getUsername()){
                return true;
            }
        }
        return false;
    }
}
