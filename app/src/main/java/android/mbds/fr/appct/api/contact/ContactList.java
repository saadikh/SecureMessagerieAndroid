package android.mbds.fr.appct.api.contact;

import android.content.SharedPreferences;
import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.model.ReceivedMsg;
import android.mbds.fr.appct.api.service.RetrofitInstance;
import android.mbds.fr.appct.api.service.UserClient;
import android.mbds.fr.appct.utils.PreferencesManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactList extends Fragment {
    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    private static List<Profile> contactListData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.fragment_contact_list, container);

        final FragmentActivity fragmentBelongActivity = getActivity();

        if (retView != null) {

            // Click this button will show a Toast popup.
            /*Button iosButton = (Button) retView.findViewById(R.id.fragmentLeftButtonIos);
            iosButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(fragmentBelongActivity, "jjkbiybuj", Toast.LENGTH_SHORT).show();
                }
            });
*/

            // Click this button will show an alert dialog.
            /*Button windowsButton = (Button) retView.findViewById(R.id.fragmentLeftButtonWindows);
            windowsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog alertDialog = new AlertDialog.Builder(fragmentBelongActivity).create();
                    alertDialog.setMessage("You click Windows button.");
                    alertDialog.show();
                }
            });*/

            // Contact List
            recyclerView = (RecyclerView) retView.findViewById(R.id.contactList);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            getMessages();

        }


        return retView;
    }
    private void getMessages() {
        String token = PreferencesManager.getInstance(getContext()).loadAccessToken().trim();

        UserClient userClient = RetrofitInstance.getRetrofitInstance().create(UserClient.class);
        Call<List<ReceivedMsg>> call = userClient.getMessages("Bearer " + token);
        call.enqueue(new Callback<List<ReceivedMsg>>() {
            @Override
            public void onResponse(Call<List<ReceivedMsg>> call, Response<List<ReceivedMsg>> response) {
                // Get unique Users with last msg
                Map<ReceivedMsg, String> usersMap= new HashMap<>();

                Map<String, List<ReceivedMsg>> tmp= new HashMap<>();
                for(ReceivedMsg msg: response.body()){
                    usersMap.put(msg, msg.getAuthor());
                }

                Iterator<Map.Entry<ReceivedMsg, String>> i = usersMap.entrySet().iterator();
                while (i.hasNext()) {
                    Map.Entry<ReceivedMsg, String> next = i.next();
                    if (tmp.get(next.getValue()) != null) {
                        List<ReceivedMsg> msg= tmp.get(next.getValue());
                        msg.add(next.getKey());
                        tmp.put(next.getValue(), msg);
                    }else{
                        List<ReceivedMsg> msg = new ArrayList<>();
                        msg.add(next.getKey());
                        tmp.put(next.getValue(), msg);
                    }
                }
                // Sort by dateCreated
                for (Map.Entry<String, List<ReceivedMsg>> entry : tmp.entrySet())
                {
                    Log.i(entry.getKey() , "===================");//insert
                    for(ReceivedMsg rm : entry.getValue()) {
                        //test sur getidmsg() et insertion
                        Log.i(rm.getAuthor(), rm.getDateCreated() + " - " + rm.getMsg());
                    }
                }

                adapter = new RecyclerViewAdapter(tmp);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ReceivedMsg>> call, Throwable t) {
                Log.i("GetMessages error", ""+t);
                Toast.makeText(getActivity(), "GetMessages error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
