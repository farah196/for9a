package com.example.farahalkiswani.for9a;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Farah alkiswani on 7/9/2018.
 */

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText _emailText;
    private EditText _passwordText;
    private Button _loginButton;
    private TextView ChangePassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.login );
        Log.d( TAG, "done" );

        _emailText = findViewById( R.id.input_email );
        _passwordText = findViewById( R.id.input_password );
        _loginButton = findViewById( R.id.btn_login );
        ChangePassword = findViewById( R.id.textView2 );

        ChangePassword.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Login.this, resetPassword.class );
                startActivity( intent );
            }
        } );
        _loginButton.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                login();
            }
        } );

        if (SaveSharedPreference.getLoggedStatus( Login.this )) {
            Intent intent = new Intent( Login.this, MainActivity.class );
            startActivity( intent );
        }



    }



    public void login() {

        Log.d( TAG, "Login" );

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();




        APIService1 service = ApiClient.getClient().create( APIService1.class );

        Call <JsonObject> userCall = service.userLogIn( email, password );

        userCall.enqueue( new Callback <JsonObject>() {
            @Override
            public void onResponse(@NonNull Call <JsonObject> call, @NonNull Response <JsonObject> response) {
                Log.i( "getTimeSessions", Integer.toString( response.code() ) );
                if (response.isSuccessful()) {
                    SaveSharedPreference.setLoggedIn( getApplicationContext(), response.body().toString() );
                  String headerList = response.headers().get( "Authentication" );
                  Log.d("0000",headerList);

                    Intent in = new Intent( Login.this, MainActivity.class );
                    startActivity( in );
                } else {
                    Log.d( TAG, "NOt success" );
                    openDialog();

                }
            }

            @Override
            public void onFailure(Call <JsonObject> call, Throwable t) {
                Log.d( TAG, "Wrong email or password" );
            }
        } );
    }

    public void openDialog() {
        final Dialog dialog = new Dialog( Login.this );
        dialog.setContentView( R.layout.alert );
        dialog.setTitle( R.string.dialog_title );
        Button cancel1 = dialog.findViewById( R.id.dialog_cancel );
        dialog.show();

        cancel1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }

        } );

    }

}


