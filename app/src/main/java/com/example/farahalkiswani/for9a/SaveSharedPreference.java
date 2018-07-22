package com.example.farahalkiswani.for9a;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Farah alkiswani on 7/11/2018.
 */

public class SaveSharedPreference {
    public static final String LOGGED_IN_PREF = "logged_in_status";

    static SharedPreferences getPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences( context );
    }


    public static void setLoggedIn(Context context, String userInfo) {
        SharedPreferences.Editor editor = getPreferences( context ).edit();
        editor.putBoolean( LOGGED_IN_PREF, true );
        try {
            JSONObject obj = new JSONObject( userInfo );
            editor.putInt( "id", obj.getInt( "id" ) );
            editor.putString( "first_name", obj.getString("first_name"));
            editor.putString( "last_name", obj.getString( "last_name" ) );
            editor.putString( "email", obj.getString( "email" ) );
            editor.putInt( "status", obj.getInt( "status" ) );
            editor.putString( "pic", obj.getString( "pic" ) );
            editor.putInt( "show_steps", obj.getInt( "show_steps" ) );
            editor.putString("gender", obj.getString( "gender" ) );
            editor.apply();

        } catch (JSONException e)
        {
            e.printStackTrace();
        }


        editor.apply();
    }


    static boolean getLoggedStatus(Context context) {

     return   PreferenceManager.getDefaultSharedPreferences(context).getAll().containsKey( LOGGED_IN_PREF );

    }


    static void loggout(Context context) {

        PreferenceManager.getDefaultSharedPreferences(context).
                edit().clear().apply();
    }

}
