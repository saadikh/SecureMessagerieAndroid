package android.mbds.fr.appct.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

//class for auto starting my service
public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction()))
        {
            Intent i = new Intent(context,RemoteService.class);
            context.startService(i);
        }

    }
}
