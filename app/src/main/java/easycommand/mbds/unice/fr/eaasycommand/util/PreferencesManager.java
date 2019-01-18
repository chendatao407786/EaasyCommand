package easycommand.mbds.unice.fr.eaasycommand.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesManager {
    private static PreferencesManager ourInstance;

    private SharedPreferences sharedPreferences;

    //Nom des shared
    private static final String PREFS_NAME = "myPreferences";

    //Valeurs a stocker
    private static final String PREFS_USERNAME = "prefs_username";
    private static final String PREFS_EMAIL = "prefs_email";
    private static final String PREFS_PASSWORD = "prefs_password";
    private static final String PREFS_TOKEN = "prefs_token";




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
        sharedPreferences.edit().putString(PREFS_USERNAME, username).apply();
    }

    public String loadUsername(){
        return sharedPreferences.getString(PREFS_USERNAME, null);
    }

    public void saveEmail(String email){
        sharedPreferences.edit().putString(PREFS_EMAIL, email).apply();
    }
    public String loadEmail(){
        return sharedPreferences.getString(PREFS_EMAIL,null);
    }
    public void savePwd(String password){
        sharedPreferences.edit().putString(PREFS_PASSWORD, password).apply();
    }

    public String loadPwd(){
        return sharedPreferences.getString(PREFS_PASSWORD, null);
    }



    public void saveToken(String accessToken){
        sharedPreferences.edit().putString(PREFS_TOKEN, accessToken).apply();
    }

    public String loadToken(){
        return sharedPreferences.getString(PREFS_TOKEN, null);
    }

}
