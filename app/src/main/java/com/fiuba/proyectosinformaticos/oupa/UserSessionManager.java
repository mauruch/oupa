package com.fiuba.proyectosinformaticos.oupa;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fiuba.proyectosinformaticos.oupa.activities.LoginActivity;
import com.fiuba.proyectosinformaticos.oupa.model.UserLogged;
import com.fiuba.proyectosinformaticos.oupa.model.UserSession;


public class UserSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    private Context context;

    // Shared preferences mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    public static final String PREFER_NAME = "Reg";

    // All Shared Preferences Keys
    public static final String IS_USER_LOGIN = "isUserLoggedIn";

    public static final String KEY_FIRST_NAME = "firstName";
    public static final String KEY_LAST_NAME = "lastName";
    public static final String KEY_TOKEN = "accessToken";


    public UserSessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREFER_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void saveUserLogged(UserLogged userLogged, String accessToken){
        editor.putBoolean(IS_USER_LOGIN, true);
        // Storing name in preferences
        editor.putString(KEY_FIRST_NAME, userLogged.firstName);
        editor.putString(KEY_LAST_NAME, userLogged.lastName);
        editor.putString(KEY_TOKEN, accessToken);

        // commit changes
        editor.commit();
    }

    public String getAuthorizationToken() {
        String accessToken = pref.getString(KEY_TOKEN, null);
        return accessToken;
    }

    public String getFullName() {
        String name = pref.getString(KEY_FIRST_NAME, null);
        String lastName = pref.getString(KEY_LAST_NAME, null);
        return name + " " + lastName;
    }

    public boolean isUserLoggedIn(){
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

    /**
     * Clear session details
     * */
    public void logout() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to MainActivity
        Intent i = new Intent(context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        context.startActivity(i);
    }

}
