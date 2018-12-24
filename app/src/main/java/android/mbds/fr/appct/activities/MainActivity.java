package android.mbds.fr.appct.activities;

import android.mbds.fr.appct.R;
import android.mbds.fr.appct.fragments.MessageFragment;
import android.mbds.fr.appct.fragments.MessageListFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity{

    FrameLayout frameMessageList;
    FrameLayout frameMessageItem;
    Button btn;
    boolean a = true;

    MessageListFragment mlf = new MessageListFragment();
    MessageFragment mf = new MessageFragment();

    boolean isSingleViewed=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*frameMessageList = (FrameLayout) findViewById((R.id.messageFragment)) ;
        frameMessageItem = (FrameLayout) findViewById((R.id.messageFragment2)) ;

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(frameMessageList.getId(), mlf);
        fragmentTransaction.commit();*/
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SIGN_IN_REQUEST_CODE) {
            if(resultCode == RESULT_OK) {
                Toast.makeText(this,
                        "Successfully signed in. Welcome!",
                        Toast.LENGTH_LONG)
                        .show();
                displayChatMessages();
            } else {
                Toast.makeText(this,
                        "We couldn't sign you in. Please try again later.",
                        Toast.LENGTH_LONG)
                        .show();

                // Close the app
                finish();
            }
        }

    }*/
}
