package android.mbds.fr.appct.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.activities.MainActivity;
import android.mbds.fr.appct.activities.ContactActivity;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.Contact;
import android.mbds.fr.appct.utils.ContactAdapter;
import android.mbds.fr.appct.utils.ItemClickSupport;
import android.mbds.fr.appct.utils.iCallable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ContactListFragment extends Fragment {

    ContactAdapter contactAdapter;
    RecyclerView recyclerView;
    Database db;

    
    String dataSended;
    iCallable mCallable;

    ArrayList<Contact> result= new ArrayList<>();
    FloatingActionButton fct;
    public ContactListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        db= Database.getIstance(getActivity().getApplicationContext());
        result = db.readContact();

        contactAdapter = new ContactAdapter(result);

        View v = inflater.inflate(R.layout.fragment_contact_listbis, container, false);

        fct = (FloatingActionButton)v.findViewById(R.id.btn_add_contact);

        fct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact();
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_contact); //reference vers fragment_contact_list
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));//for displaying  correctly

        this.configureOnClickRecyclerView();

        return v;
    }


    public void addContact(){
        Intent intent = new Intent(getActivity(), ContactActivity.class);
        startActivity(intent);
    }

    public void sendMessage(){
        mCallable.sendContact(dataSended);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallable = (iCallable) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+ " must implement iCallable") ; }
    }

    //********************************



    // 1 - Configure item click on RecyclerView

    private void configureOnClickRecyclerView(){

        ItemClickSupport.addTo(recyclerView, R.layout.contact_cellulite)

                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {

                    @Override

                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        Contact contact = contactAdapter.getContact(position);
                        dataSended = contact.getUsername();
                        mCallable.sendContact(dataSended);

                        if((getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)){
                            /*Bundle tmp = new Bundle();
                            tmp.putString("username", dataSended);
                            mf.setArguments(tmp);*/
                            //setInfoClick(true);
                            isClickedItem();

                            //Toast.makeText(getContext(), "You clicked on user : "+dataSended, Toast.LENGTH_SHORT).show();

                        }

                    }

                });
    }

    public void isClickedItem(){
        Intent i = new Intent(getActivity().getBaseContext(), MainActivity.class);
        i.putExtra("username", dataSended);
        getActivity().startActivity(i);
    }


}

