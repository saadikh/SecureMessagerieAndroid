package android.mbds.fr.appct.fragments;


import android.content.Context;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.database.Database;
import android.mbds.fr.appct.models.ChatMessage;
import android.mbds.fr.appct.utils.MessageAdapter;
import android.mbds.fr.appct.utils.PreferencesManager;
import android.mbds.fr.appct.utils.iCallable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MessageListFragment extends Fragment {
    private UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);


    MessageAdapter messageAdapter;
    RecyclerView recyclerView;
    Database db;
    iCallable mCallback;

    ArrayList<ChatMessage> result= new ArrayList<>();


    public MessageListFragment() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*db= Database.getIstance(this.getContext());
        result = db.readMessage();*/
        /*ChatMessage cm1 = new ChatMessage("salut", "thiaw");
        ChatMessage cm2 = new ChatMessage("resalut", "awa");
        ArrayList<ChatMessage> result = new ArrayList<>();
        result.add(cm1);
        result.add(cm2);*/

        messageAdapter = new MessageAdapter(result);

        View v =inflater.inflate(R.layout.fragment_message_list, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view); //reference vers fragment_message_list
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(messageAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });*/

        return v;
    }

    //methode lance lorsque ce fragment est mis dans l'activity
    public void onAttach(Context context)
    {
        super.onAttach(context);

        if(context instanceof iCallable)
        {
            mCallback = (iCallable) context;
        }
        else
        {
            throw new ClassCastException(context.toString()+ " must implement iCallable");
        }
    }

    /*private void getMessages() {
        String token = PreferencesManager.getInstance(getContext()).loadAccessToken().trim();

        Call<ResponseBody> call = userClient.getMessages("Bearer " + token);
        call.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i("Error String", "" + response);
                try {
                    Toast.makeText(getContext(), response.body().string(), Toast.LENGTH_SHORT).show();
                    traitement
                    for(int i=0; i<response.body().contentLength(); i++){
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "GetMessages error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
