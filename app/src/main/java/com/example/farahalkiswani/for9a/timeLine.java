package com.example.farahalkiswani.for9a;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Farah alkiswani on 7/12/2018.
 */

public class timeLine  extends AppCompatActivity {
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Desc = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.timeline );


        RecyclerView recyclerView = (RecyclerView) findViewById( R.id.timelineRecyleVeiw );
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        Adapter mAdapter = new Adapter( this, Names, Desc );

        recyclerView.setAdapter( mAdapter );
Names.add( "hiii" );
Log.d ("00","hii");
Names.add( "Byee" );
Desc.add( "whyy" );
Desc.add( "Hoow" );



        mAdapter.notifyDataSetChanged();

       // prepareMovieData(); bnady feeh    mAdapter.notifyDataSetChanged();  movieList.add(movie);
    }

}
