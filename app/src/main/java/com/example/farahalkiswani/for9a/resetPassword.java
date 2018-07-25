package com.example.farahalkiswani.for9a;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Farah alkiswani on 7/11/2018.
 */

public class resetPassword extends AppCompatActivity {


    private EditText Email;
    private  Button Ok ;
    private Button cancel ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.reset_password );
        Email= findViewById( R.id.resetEmail );

        Ok = findViewById( R.id.reset_btn );


        Ok.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetpassword();

                Intent intent = new Intent( resetPassword.this, SuccessResetPassword.class );
                startActivity( intent );
            }
        } );

    }

    private void resetpassword() {

        String email = Email.getText().toString();


           APIService1 service = ApiClient.getClient().create( APIService1.class );

            Call<JsonObject> userCall = service.userChangePassword( email );

            userCall.enqueue( new Callback<JsonObject>() {
                @Override
                public void onResponse(@NonNull Call <JsonObject> call, @NonNull Response<JsonObject> response) {
                    Log.i( "getTimeSessions", Integer.toString( response.code() ) );
                    if (response.isSuccessful()) {


                        Intent in = new Intent( resetPassword.this, SuccessResetPassword.class );
                        startActivity( in );
                    } else {
                        Log.d( "fail", "NOt success" );


                    }
                }

                @Override
                public void onFailure(Call <JsonObject> call, Throwable t) {
                    Log.d( "fail", "Wrong email or password" );
                }
            } );
        }

        }




