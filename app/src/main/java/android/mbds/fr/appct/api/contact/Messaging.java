package android.mbds.fr.appct.api.contact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.TalkActivity;
import android.mbds.fr.appct.api.model.Message;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.utils.PreferencesManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Messaging extends Fragment {
    // This method will be invoked when the Fragment view object is created.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_messaging, container);

        if (retView != null) {
            TextView receiverTxt = retView.findViewById(R.id.receiver);
            receiverTxt.setText("Aucun Receiver selectionné");
        }

        ListView messagesList = retView.findViewById(R.id.msgList);
        String[] demoData = new String[]{"Chargement des messages ..."};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getContext(), R.layout.messaging_list_item, R.id.itemTxt, demoData);
        messagesList.setAdapter(adapter);

        final TextView receiver = retView.findViewById(R.id.receiver);
        final TextView msgTxt = retView.findViewById(R.id.sendTxt);

        retView.findViewById(R.id.sendBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage(""+msgTxt.getText(), ""+receiver.getText());
            }
        });

        return retView;
    }

    private void sendMessage(String message, String receiver) {
        String token = PreferencesManager.getInstance(getContext()).loadAccessToken().trim();

        UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);
        Call<ResponseBody> call = userClient.sendMessage("Bearer " + token, new Message(message, receiver));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        Toast.makeText(getActivity(), response.body().string(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(), TalkActivity.class);
                        startActivity(intent);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.i("Send error", ""+response.message());
                    Toast.makeText(getActivity(), "Send error :/\n" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("Send error", ""+t);
                Toast.makeText(getActivity(), "Send error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
