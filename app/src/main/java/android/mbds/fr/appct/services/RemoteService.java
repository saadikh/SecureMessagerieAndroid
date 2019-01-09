package android.mbds.fr.appct.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

public class RemoteService extends Service {

    private Handler mHandler;
    // default interval for syncing data
    public static final long DEFAULT_SYNC_INTERVAL = 5 * 1000;

    //autogene
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // task to be run here
    private Runnable runnableService = new Runnable() {
        @Override
        public void run() {
            //syncData();
            // Repeat this runnable code block again every ... min
            mHandler.postDelayed(runnableService, DEFAULT_SYNC_INTERVAL);
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Create the Handler object
        mHandler = new Handler();
        // Execute a runnable task as soon as possible
        mHandler.post(runnableService);
        return START_STICKY;
    }

}
