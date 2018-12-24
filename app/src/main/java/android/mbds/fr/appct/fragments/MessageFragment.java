package android.mbds.fr.appct.fragments;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.models.ChatMessage;
import android.mbds.fr.appct.utils.MessageAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MessageFragment extends Fragment {

    MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    ChatMessage chatMessage;
    private TextView recipientMessage;
    FloatingActionButton btnNewMsg;
    TextView txtMessage;
    String str;

    public MessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_message_item, container, false);
        recipientMessage = (TextView)v.findViewById(R.id.recepientMessage);
        txtMessage = (TextView)v.findViewById(R.id.boxMessage);
        btnNewMsg = (FloatingActionButton)v.findViewById(R.id.btn_add_msg);

        Toast.makeText(getContext(), "You clicked on user : "+this.str, Toast.LENGTH_SHORT).show();

        return  v;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }


    public TextView getRecipientMessage(){
        return recipientMessage;
    }


    @Override
    public void onResume() {//for saving data
        super.onResume();
        //this.txtMessage.setText(chatMessage.getMessageTxt());
        if(this.recipientMessage != null){
            this.recipientMessage.setText(this.str);

        }
    }

    public void setRecipientMessage(String s){
        String res = s.toUpperCase();

        if(this.recipientMessage != null)
        {
            this.recipientMessage.setText(res);

        }
        this.str = res;

    }

    // pour sauver le destinataire apres changement d'orientation
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }

}
