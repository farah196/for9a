package com.example.farahalkiswani.for9a;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class oppertiniutyList extends AppCompatActivity {

    ArrayList<String> Names1 = new ArrayList<>();
    ArrayList<String> Desc1 = new ArrayList<>();
    ArrayList<String> img1 = new ArrayList<>();
    ArrayList<String> viewnum1 = new ArrayList<>();
    ArrayList<Integer> Pin1 = new ArrayList<>();
    ArrayList<Integer> ID1 = new ArrayList<>();
    ArrayList<Integer> isPinned1 = new ArrayList<>();
    List<String> Orgimage1 = new ArrayList<>();
    List<String> OrgName1 = new ArrayList<>();
    List<String> OrgCat1 = new ArrayList<>();
    List<String> Full1 = new ArrayList<>();

    int id = 0;
    listAdapter mAdapter1;
    ImageView toGrid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        toGrid = findViewById(R.id.imageView111);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        mAdapter1 = new listAdapter(oppertiniutyList.this, Names1, Desc1, img1, Pin1, ID1, isPinned1, viewnum1,
                Orgimage1, Full1, OrgName1);
        recyclerView.setAdapter(mAdapter1);


        final LinearLayoutManager LayoutManager = new LinearLayoutManager(oppertiniutyList.this);
        recyclerView.setLayoutManager(LayoutManager);
       // recyclerView.addItemDecoration(new oppertiniutyList.(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setupWindowAnimations();

        getOppertinuity();
        System.out.println(id + "old ");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    System.out.println(id);

                    getOppertinuity();

                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });

        toGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(oppertiniutyList.this, MainActivity.class);


                startActivity(intent);


            }
        });
    }

    private void getOppertinuity() {


        Log.d("status", "get Oppertinuty");

        String Name = getIntent().getStringExtra("nameo");
        String image = getIntent().getStringExtra("imgo");
        String full = getIntent().getStringExtra("full");
        String Oimage = getIntent().getStringExtra("OrgImg");
        String NameOrg = getIntent().getStringExtra("OrgName");
        int pin = getIntent().getIntExtra("pin",0);
        String catOrg = getIntent().getStringExtra("cat");
        String view = getIntent().getStringExtra("view");
        int ispin = getIntent().getIntExtra("is",0);

        System.out.println(Name);
        Names1.add(Name);
        Pin1.add(pin);
        img1.add(image);
        Orgimage1.add(Oimage);
        OrgName1.add(NameOrg);
        ID1.add(id);

       OrgCat1.add(catOrg);
        viewnum1.add(view);
        isPinned1.add(ispin);
        Full1.add(full);


        mAdapter1.notifyDataSetChanged();


    }


    private void setupWindowAnimations() {


        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.toolbar11), true);
        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


    }
}
