package easycommand.mbds.unice.fr.eaasycommand;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import easycommand.mbds.unice.fr.eaasycommand.api.AuthClient;
import easycommand.mbds.unice.fr.eaasycommand.api.RetrofitInstance;
import easycommand.mbds.unice.fr.eaasycommand.api.model.Auth;
import easycommand.mbds.unice.fr.eaasycommand.util.PreferencesManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private AuthClient authClient = RetrofitInstance.getRetrofitInstance().create(AuthClient.class);
    EditText mUsername;
    EditText mPassword;
    Button mSignUpButton;
    Button mSignInButton;
    private  View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.signup_button:
                    Intent intent = new Intent(SignInActivity.this,SignUpActivity.class);
                    startActivity(intent);
                    return;
                case R.id.signin_button:
                    String username = PreferencesManager.getInstance(getApplicationContext()).loadUsername();
                    String password = PreferencesManager.getInstance(getApplicationContext()).loadUsername();

                    if((username != null) && (password != null)){ //if exist data of the current user
                        signIn(username, password);
                    }else{ //to get values of form
                        signIn(mUsername.getText().toString().trim(),mPassword.getText().toString().trim());

                    }

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mUsername = findViewById(R.id.username);
        mPassword = findViewById(R.id.password);
        mSignInButton = findViewById(R.id.signin_button);
        mSignInButton.setOnClickListener(mOnClickListener);

        mSignUpButton = findViewById(R.id.signup_button);
        mSignUpButton.setOnClickListener(mOnClickListener);
    }

    public void signIn(final String username, final String password){
        Call<ResponseBody> call = authClient.signIn(new Auth(username,password));
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        Toast.makeText(SignInActivity.this, "Connected successfully", Toast.LENGTH_SHORT).show();

                        //to save data of current user for the next connexion
                        PreferencesManager.getInstance(getApplicationContext()).saveUsername(username);
                        PreferencesManager.getInstance(getApplicationContext()).savePwd(password);

                        Intent intent = new Intent(SignInActivity.this, NfcReadActivity.class);
                        /*Bundle bundle = new Bundle();
                        bundle.putString("username",res.getString("username"));
                        bundle.putString("email",res.getString("email"));
                        intent.putExtras(bundle);*/
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(SignInActivity.this, "CreateUser error :/\n"+response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(SignInActivity.this, "CreateUser error :/\n" + t, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
