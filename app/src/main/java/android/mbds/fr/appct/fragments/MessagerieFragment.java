package android.mbds.fr.appct.fragments;

import android.content.Intent;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.activities.MainActivity;
import android.mbds.fr.appct.api.model.Message;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.utils.CryptoManager;
import android.mbds.fr.appct.utils.PreferencesManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;


public class MessagerieFragment extends Fragment {
    private UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);

    //old conversation
   /* MessageAdapter messageAdapter;
    private RecyclerView recyclerView;
    Database db;
    ChatMessage chatMessage;
    ArrayList<ChatMessage> result= new ArrayList<>();*/

    //new conversation
    TextView textViewReceiver;
    FloatingActionButton btnNewMsg;
    EditText txtMessage;
    String str;

    public MessagerieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //old conversation
        /*db= Database.getIstance(this.getContext());
        result = db.readMessage();
        ChatMessage cm1 = new ChatMessage("Test", "Marouane");
        result.add(cm1);

        messageAdapter = new MessageAdapter(result);*/
        

        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_messagerie, container, false);

        textViewReceiver = (TextView)v.findViewById(R.id.receiver);
        txtMessage = (EditText) v.findViewById(R.id.boxMessage);
        btnNewMsg = (FloatingActionButton)v.findViewById(R.id.btn_add_msg);

        btnNewMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String msg = txtMessage.getText().toString();
                //String rcv = textViewReceiver.getText().toString();
                String res = CryptoManager.getInstance().encrypter("myKeyStore", txtMessage.getText().toString());
                String resfinal = "ct(chen_thiaw)[|]MSG[|]"+res;

                sendMessage(resfinal, str);
                /*String token = PreferencesManager.getInstance(getContext()).loadAccessToken().trim();
                Toast.makeText(getContext(), token, Toast.LENGTH_SHORT).show();*/


            }
        });

        //for list conversation
        /*recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view_message);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));*/

        //Toast.makeText(getContext(), "You clicked on user : "+this.str, Toast.LENGTH_SHORT).show();

        return  v;
    }

    /*public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }*/


    public TextView getRecipientMessage(){
        return textViewReceiver;
    }


    @Override
    public void onResume() {//for saving data
        super.onResume();
        if(this.textViewReceiver != null){
            this.textViewReceiver.setText(this.str);

        }
    }

    public void setRecipientMessage(String s){
        //String res = s.toUpperCase();

        if(this.textViewReceiver != null)
        {
            this.textViewReceiver.setText(s);

        }
        this.str = s;

    }

    // pour sauver le destinataire apres changement d'orientation
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRetainInstance(true);
    }


    public void sendMessage(String message, String receiver) {
        String token = PreferencesManager.getInstance(getContext()).loadAccessToken().trim();

        Call<ResponseBody> call = userClient.sendMessage("Bearer " + token, new Message(message, receiver));
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(getContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class); //replace contact
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getContext(), "Send error :/\n" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Send error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
