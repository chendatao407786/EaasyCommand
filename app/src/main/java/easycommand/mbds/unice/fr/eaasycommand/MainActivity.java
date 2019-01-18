package easycommand.mbds.unice.fr.eaasycommand;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import easycommand.mbds.unice.fr.eaasycommand.util.PreferencesManager;

public class MainActivity extends AppCompatActivity {
    Handler mHandle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandle.postDelayed(new Runnable() {
            @Override
            public void run() {

                String username= PreferencesManager.getInstance(getApplicationContext()).loadUsername();
                String pwd = PreferencesManager.getInstance(getApplicationContext()).loadPwd();

                if(!((username == null) && (pwd == null))){
                    Toast.makeText(MainActivity.this, "Hello "+username+" !!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,NfcReadActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "SignUp please !", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this,SignInActivity.class);
                    startActivity(intent);
                }

            }
        },3000);
    }
}
