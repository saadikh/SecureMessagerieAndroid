package android.mbds.fr.appct.utils;

import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

public class CryptoManager {

    private static final String TAG = "CTApp";
    private static final String provider = "AndroidKeyStore";
    private static final String alias = "myKeyStore";

    private static CryptoManager ourInstance;

    private static KeyStore keyStore;


    public CryptoManager() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyStore.load(null);
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static CryptoManager getInstance(){
        if(ourInstance == null){
            ourInstance = new CryptoManager();
        }
        return ourInstance;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public PublicKey generateMyRsaKey(){

        try {
            if(!keyStore.containsAlias(alias)){
                KeyPairGenerator kpg = null;
                try {
                    kpg = KeyPairGenerator.getInstance("RSA", provider);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                }

                KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1);
                try {
                    kpg.initialize(builder.build());
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                }

                KeyPair kp = kpg.genKeyPair();

                KeyStore.Entry entry = null;
                try {
                    entry = keyStore.getEntry(alias, null);
                } catch (KeyStoreException | UnrecoverableEntryException | NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }

                PublicKey publicKey = null;
                try {
                    publicKey = keyStore.getCertificate(alias).getPublicKey();
                } catch (KeyStoreException e) {
                    e.printStackTrace();
                }

                return publicKey;

            }
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }

        // alias keystore exist
        KeyStore.PrivateKeyEntry privateKeyEntry = null;
        try {
            privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableEntryException e) {
            e.printStackTrace();
        }
        RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey(); //for getting publickey
        return publicKey;
    }

    public String encrypter(String alias, String res){
        //must use piblickey for ciphering msg
        String resfinal = "";
        try {
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)keyStore.getEntry(alias, null);
            RSAPublicKey publicKey = (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey(); //for getting publickey


            Cipher inCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding", "AndroidOpenSSL");
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            CipherOutputStream cipherOutputStream = new CipherOutputStream(
                    outputStream, inCipher); //for complexed ciphering
            cipherOutputStream.write(res.getBytes("UTF-8"));
            cipherOutputStream.close();

            byte [] vals = outputStream.toByteArray();
            resfinal = Base64.encodeToString(vals, Base64.DEFAULT); //for displaying
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }
        return resfinal;
    }

    public String decrypter(String alias, String res){
        String resfinal = "";
        try {
            KeyStore.Entry entry = keyStore.getEntry(alias, null);
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)entry;

            //RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();

            Cipher output = Cipher.getInstance("RSA/ECB/PKCS1Padding"); //init ciphering like encrypter
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.getPrivateKey());

            CipherInputStream cipherInputStream = new CipherInputStream(
                    new ByteArrayInputStream(Base64.decode(res, Base64.DEFAULT)), output);

            ArrayList<Byte> values = new ArrayList<>();
            int nextByte;
            while ((nextByte = cipherInputStream.read()) != -1) {
                values.add((byte)nextByte);
            }

            byte[] bytes = new byte[values.size()];
            for(int i = 0; i < bytes.length; i++) {
                bytes[i] = values.get(i).byteValue();
            }

            resfinal = new String(bytes, 0, bytes.length, "UTF-8");

        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
        }

        return resfinal;
    }

}
