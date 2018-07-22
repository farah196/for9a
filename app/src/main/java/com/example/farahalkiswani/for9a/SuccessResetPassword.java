package com.example.farahalkiswani.for9a;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Farah alkiswani on 7/12/2018.
 */

public class SuccessResetPassword extends AppCompatActivity {
    private Button Done ;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.success_reset_password );
        Done = findViewById( R.id.Done );

        Done.setOnClickListener( new View.OnClickListener() {

    @Override
    public void onClick(View v) {
        Intent intent = new Intent( SuccessResetPassword.this, Login.class );
        startActivity( intent );
    }
  } );
    }
}
