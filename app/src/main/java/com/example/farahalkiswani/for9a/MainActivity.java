package com.example.farahalkiswani.for9a;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
   private TextView name;
   private TextView Email;
   private TextView gender;
   private  ImageView image;
   private Button Logout;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        name = findViewById( R.id.name );
        Email = findViewById( R.id.email );
        gender = findViewById( R.id.gender );
        image = findViewById( R.id.img );
        Logout = findViewById( R.id.button2 );
        Logout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveSharedPreference.loggout( MainActivity.this );

                Intent in = new Intent( MainActivity.this, Login.class );
                startActivity( in );
            }
        } );



        JSONObject obj ;


        SharedPreferences sharedPref = getSharedPreferences( "userInfoFile", Context.MODE_PRIVATE );

        String getInfo = sharedPref.getString( "userInfo", "" );
        try {
            obj = new JSONObject( getInfo );


            String firstName = obj.getString( "first_name" );
            String lastName = obj.getString( "last_name" );
            name.setText( firstName + " " + lastName );

            String email = obj.getString( "email" );
            Email.setText( email );
            String gender1 = obj.getString( "gender" );
            if (gender1.equals( null )) gender.setText( "   " );
            gender.setText( gender1 );


            String pic = obj.getString( "pic" );


            Picasso.with( this ).load( pic ).into( image );

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}