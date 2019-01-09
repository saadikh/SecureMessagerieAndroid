package android.mbds.fr.appct.services;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class MyServices extends Service {

    private static final String PREFS = "ct";
    private static final String PREFS_PUBLIC_KEY = "PREFS_NAME";
    private static final String PREFS_PRIVATE_KEY = "PREFS_NAME";
    SharedPreferences sharedPreferences;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);
        sharedPreferences = getBaseContext().getSharedPreferences(PREFS, MODE_PRIVATE);
        if(sharedPreferences.contains(PREFS_PRIVATE_KEY) && sharedPreferences.contains(PREFS_PUBLIC_KEY)){
            String publicKey = sharedPreferences.getString("PREFS_PUBLIC_KEY", null);
            String privateKey = sharedPreferences.getString("PREFS_PRIVATE_KEY", null);
            Toast.makeText(this, "PBK: " + publicKey + " PRK: " + privateKey, Toast.LENGTH_SHORT).show();
        }{
            String provider = "ct_keystore";
            String transformation = "RSA/ECB/PKCS1Padding";

            KeyPairGenerator kpg = null;
            try {
                kpg = KeyPairGenerator.getInstance("RSA", provider);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (NoSuchProviderException e) {
                e.printStackTrace();
            }
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder("ct_keystore_alias", KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
            try {
                kpg.initialize(builder.build());
            } catch (InvalidAlgorithmParameterException e) {
                e.printStackTrace();
            }
            KeyPair kp = kpg.genKeyPair();


        }
        return START_STICKY;

    }
}
