package android.mbds.fr.appct.activities;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.Contact;
import android.mbds.fr.appct.utils.ContactAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {

    ContactAdapter contactAdapter;
    RecyclerView recyclerView;
    Database db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ArrayList<Contact> result= new ArrayList<>();

        db= Database.getIstance(getApplicationContext());
        result = db.readContact();

        contactAdapter = new ContactAdapter(result);


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_test);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        /*mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);*/

        recyclerView.setLayoutManager(new LinearLayoutManager(this));//for displaying  correctly

        // specify an adapter (see also next example)
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(contactAdapter);

        /*mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);*/

    }
}
