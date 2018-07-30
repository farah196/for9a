package com.example.farahalkiswani.for9a;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.transition.Fade;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.annotation.Target;

public class InfoActivity extends AppCompatActivity {

    ImageView imgDesc;
    TextView nameDesc;
    String Name;
    String URL;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        {
            imgDesc = findViewById(R.id.imageshare);
            nameDesc = findViewById(R.id.titleshare);
            Name = getIntent().getStringExtra("name");
            URL = getIntent().getStringExtra("img");
            nameDesc.setText(Name);
            Picasso.with(this).load(URL).into(imgDesc);
            initPage();
        }

    }

    private void initPage() {


        final ScrollView revealDemo = (ScrollView) findViewById(R.id.layout);
        revealDemo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                makeCircularRevealAnimation(revealDemo);
            }
        });
    }

    private void makeCircularRevealAnimation(View view) {

        final TextView textDesc = (TextView) findViewById(R.id.textdesc);

        int centerX = (view.getLeft() + view.getRight()) / 2;
        int centerY = (view.getTop() + view.getBottom()) / 2;

        float radius = Math.max(textDesc.getWidth(), textDesc.getHeight()) * 2.0f;

        if (textDesc.getVisibility() == View.INVISIBLE) {
            textDesc.setVisibility(View.VISIBLE);
            textDesc.setText("description");

            ViewAnimationUtils.createCircularReveal(textDesc, centerX, centerY, 0, radius).start();
        } else {
            Animator reveal = ViewAnimationUtils.createCircularReveal(textDesc, centerX, centerY, radius, 0);
            reveal.addListener(new AnimatorListenerAdapter() {
                public void onAnimationEnd(Animator animation) {
                    textDesc.setVisibility(View.INVISIBLE);
                }
            });
            reveal.start();
        }
    }

}