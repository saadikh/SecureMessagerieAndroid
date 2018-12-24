package android.mbds.fr.appct.activities;

import android.content.Intent;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.Contact;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class ContactNewActivity extends AppCompatActivity {

    EditText etUsername;
    Button btnSave;
    private Database db;

    boolean isSaved = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_new);

        etUsername = (EditText)findViewById(R.id.ctUsernameBox);
        btnSave = (Button)findViewById(R.id.btnNewContact);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
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

    public void addContact(){
        db= Database.getIstance(this);
        db.addContact(etUsername.getText().toString());

        //redirect to login page
        Intent intent = new Intent(this, ContactActivity.class);
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
