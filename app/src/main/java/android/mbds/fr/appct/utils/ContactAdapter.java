package android.mbds.fr.appct.utils;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.models.Contact;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {


    private ArrayList<Contact> contacts;

    public ContactAdapter(ArrayList<Contact> contacts){
        this.contacts = contacts;
    } //************

    //pour afficher un item
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int iviewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_cellulite, parent, false); //revoir layout
        return new MyViewHolder(itemView);
    }


    //remplit MyViewHolder avec la donnee specifie
    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        holder.tvUsername.setText(contacts.get(i).getUsername());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public Contact getContact(int position)
    {
        return this.contacts.get(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tvUsername;
        //TextView timeMessage;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            tvUsername = (TextView)itemView.findViewById(R.id.contact_cellulite);        }


    }

}
