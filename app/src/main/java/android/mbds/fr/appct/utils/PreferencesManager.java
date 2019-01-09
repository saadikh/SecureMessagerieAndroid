package android.mbds.fr.appct.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.security.PublicKey;


public class PreferencesManager
{
    private static PreferencesManager ourInstance;

    private SharedPreferences sharedPreferences;

    //Nom des shared
    private static final String PREFS_NAME = "myPreferences";

    //Valeurs a stocker
    private static final String KEY_USERNAME = "key_username";
    private static final String KEY_PWD = "key_pwd";
    private static final String KEY_RSA_PUBLIC = "key_rsa_public";
    private static final String KEY_ACCESS_TOKEN = "key_access_token";
    private static final String KEY_REFRESH_TOKEN = "key_refresh_token";
    private static final String KEY_EXPIRES_IN = "key_expires_in";




    public static PreferencesManager getInstance(Context context) {

        //test and return instance class
        if(ourInstance == null){
            ourInstance = new PreferencesManager(context);
        }
        return ourInstance;
    }

    private PreferencesManager(Context context) {

        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

    }

    //********************************************************** lecture/ecriture des valeurs


    public void saveUsername(String username){
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public String loadUsername(){
        return sharedPreferences.getString(KEY_USERNAME, "ct");
    }

    public void savePwd(String password){
        sharedPreferences.edit().putString(KEY_PWD, password).apply();
    }

    public String loadPwd(){
        return sharedPreferences.getString(KEY_PWD, "ct");
    }

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.M)
    public String loadRsaPublic(){
        PublicKey publicKey = CryptoManager.getInstance().generateMyRsaKey();
        return sharedPreferences.getString(KEY_RSA_PUBLIC, publicKey.toString()); //default value

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void saveRsaPublic(String publicKey){
        sharedPreferences.edit().putString(KEY_RSA_PUBLIC, publicKey).apply();

    }

    public void saveAccessToken(String accessToken){
        sharedPreferences.edit().putString(KEY_ACCESS_TOKEN, accessToken).apply();
    }

    public String loadAccessToken(){
        return sharedPreferences.getString(KEY_ACCESS_TOKEN, "");
    }

    public void saveRefreshToken(String refreshToken){
        sharedPreferences.edit().putString(KEY_REFRESH_TOKEN, refreshToken).apply();
    }

    public String loadRefreshToken(){

        return sharedPreferences.getString(KEY_REFRESH_TOKEN, "");
    }

    public void saveExpiresIn(int expiresIn){
        sharedPreferences.edit().putInt(KEY_EXPIRES_IN, expiresIn).apply();
    }

    public int loadExpiresIn(){
        return sharedPreferences.getInt(KEY_EXPIRES_IN, 2592000);
    }


    //For contacts
    /**
     * key = name of Contact
     * value = KeyPublic of Contact
     * */
    public void saveKPContact(String nameContact, String pkContact){
        String key = "CT_KEY_PUBLIC_"+nameContact;
        sharedPreferences.edit().putString(key, pkContact).apply();
    }

    /**
     * KeyName = "KEY_" + name of contact
     * */
    public String loadKPContact(String nameContact){
        String key = "CT_KEY_PUBLIC_"+nameContact;
        return sharedPreferences.getString(key, "");
    }


    // return true if Pk of nameContact exist
    public boolean isExistPkContact(String nameContact){
        String key = "CT_KEY_PUBLIC_"+nameContact;
        return (!(sharedPreferences.getString(key, "").equals("")));
    }

    public String refreshConnexion(){
        String valideToken = loadRefreshToken();
        saveAccessToken(valideToken);
        return valideToken;
    }


}
