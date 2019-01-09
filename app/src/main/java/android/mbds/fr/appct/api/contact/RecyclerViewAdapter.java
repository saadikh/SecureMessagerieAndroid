package android.mbds.fr.appct.api.contact;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.api.model.ReceivedMsg;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class RecyclerViewAdapter extends RecyclerView.Adapter
        <RecyclerViewAdapter.ListItemViewHolder> {
    private Map<String, List<ReceivedMsg>> items; //username, list from msg
    private SparseBooleanArray selectedItems;

    RecyclerViewAdapter(Map<String, List<ReceivedMsg>> profileData) {
        if (profileData == null) {
            throw new IllegalArgumentException("profileData must not be null");
        }
        items = profileData;
        selectedItems = new SparseBooleanArray();
    }

    @Override
    public ListItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_item, viewGroup, false);
        return new ListItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ListItemViewHolder viewHolder, int position) {
        ReceivedMsg profile = items.get((items.keySet().toArray())[position]).get(0);

        viewHolder.name.setText(String.valueOf(profile.getAuthor()));
        viewHolder.lastMsg.setText(String.valueOf(profile.getMsg()));
        viewHolder.itemView.setActivated(selectedItems.get(position, false));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public final class ListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name;
        TextView lastMsg;

        public ListItemViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.txt_name);
            lastMsg = (TextView) itemView.findViewById(R.id.txt_last_msg);
        }

        @Override
        public void onClick(View view) {

            Toast.makeText(view.getContext(), "position = " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
            FragmentActivity activity = (FragmentActivity) view.getContext();
            FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment MessagingFragment = fragmentManager.findFragmentById(R.id.fragmentRight);

            TextView receiverTxt = MessagingFragment.getView().findViewById(R.id.receiver);
            receiverTxt.setText(items.get((items.keySet().toArray())[getLayoutPosition()]).get(0).getAuthor());

            ListView messagesList = MessagingFragment.getView().findViewById(R.id.msgList);
            List<String> currentMsgs = new ArrayList<>();
            for (ReceivedMsg msg : items.get((items.keySet().toArray())[getLayoutPosition()])) {
                if (msg.getAuthor().equals(receiverTxt.getText())) {
                    currentMsgs.add(msg.getMsg());
                    Log.i("messages of: " + msg.getAuthor(), msg.getMsg());
                }
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<>(activity,
                    R.layout.messaging_list_item, R.id.itemTxt, currentMsgs);
            messagesList.setAdapter(adapter);

        }
    }
}