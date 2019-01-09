package android.mbds.fr.appct.utils;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.models.ChatMessage;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

//classe affichant le contenu (elt+view) de ma recyclerview
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private ArrayList<ChatMessage> messages;

    public MessageAdapter(ArrayList<ChatMessage> messages){
        this.messages = messages;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int iviewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_messagerie, parent, false); //revoir layout
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int i) {
        ChatMessage chatMessage = messages.get(i);
        holder.display(chatMessage);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView recipientMessage;
        TextView txtMessage;
        //TextView timeMessage;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            recipientMessage = (TextView)itemView.findViewById(R.id.receiver);
            txtMessage = (TextView)itemView.findViewById(R.id.boxMessage);

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });*/

            //timeMessage  = (TextView)itemView.findViewById(R.id.timeMessage);


            //message =   (ChatMessage) itemView.findViewById(R.id.item_message);
        }

        public void display(ChatMessage chatMessage){
            recipientMessage.setText(chatMessage.getRecipientUsername());
            txtMessage.setText(chatMessage.getMessageTxt());
            //timeMessage.setText(chatMe);
        }
    }

}

