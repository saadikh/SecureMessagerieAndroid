package android.mbds.fr.appct.fragments;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.Contact;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

/**
 * fragment permettant l'ajout d'un contact dans la bd sqlite
 * */
public class ContactFragment extends Fragment {
    EditText username;
    Button btn;
    private Database db;
    private boolean isClicked = false;

    //obligatoire
    public ContactFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_contact_new, container, false);
        username = (EditText) v.findViewById(R.id.ctUsernameBox);
        btn = (Button)v.findViewById(R.id.btnNewContact);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });
        return  v;
    }

    public boolean isAddContact(){
        db= Database.getIstance(getActivity());// revoir
        List<Contact> result = db.readContact();

        if(result.size() != 0 ) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addContact(){ // send fisrt msg
        db= Database.getIstance(getActivity().getApplicationContext());
        db.addContact(username.getText().toString());
        this.setIsClicked(true);
    }

    public boolean getIsClicked(){
        return isClicked;
    }

    public void setIsClicked(boolean val){
        this.isClicked = val;
    }


}
