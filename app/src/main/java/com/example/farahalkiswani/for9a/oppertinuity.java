package com.example.farahalkiswani.for9a;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.jar.Attributes;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class oppertinuity extends Fragment {
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Desc = new ArrayList<>();
    ArrayList<String> img = new ArrayList<>();
    ArrayList<String> viewnum = new ArrayList<>();
    ArrayList<Integer> Pin = new ArrayList<>();
    ArrayList<Integer> ID = new ArrayList<>();
    ArrayList<Integer> isPinned = new ArrayList<>();
    List<String> Orgimage = new ArrayList<>();
    List<String> OrgName = new ArrayList<>();
    List<String> OrgCat = new ArrayList<>();
    List<String> Full = new ArrayList<>();

    int id = 0;
    Adapter mAdapter;
    ImageView toList;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        toList = rootView.findViewById(R.id.imageView9);
      /*  toList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( getActivity(), oppertiniutyList.class );

                Objects.requireNonNull(getActivity()).startActivity( intent );


            }
        });*/

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new Adapter(getActivity(), Names, Desc, img, Pin, ID, isPinned, viewnum, Orgimage, Full, OrgName);
        recyclerView.setAdapter(mAdapter);


        final LinearLayoutManager LayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.addItemDecoration(new oppertinuity.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        setupWindowAnimations();

        getOppertinuity(10, id);
        System.out.println(id + "old ");
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) {
                    System.out.println(id);

                    getOppertinuity(10, id);

                }

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });


        return rootView;
    }

    private void getOppertinuity(int i, int Id) {


        Log.d("status", "get Oppertinuty");


        SharedPreferences prefs = getActivity().getSharedPreferences("auth", MODE_PRIVATE);
        String authintication = prefs.getString("authclient", "MNrAG2QLv41sWs2qd-spRKT594bfwROM");
        APIService1 service = ApiClientHeader.getClient(authintication).create(APIService1.class);

        Call<JsonObject> userCall = service.getTimeline(authintication, i, Id);

        userCall.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.i("getTimeSessions", Integer.toString(response.code()));
                JSONObject obj;
                if (response.isSuccessful()) {
                    Log.i("1555", "succss");
                    try {
                        Log.i("1555", "try");
                        obj = new JSONObject(String.valueOf(response.body()));
                        JSONArray arr = obj.getJSONArray("results");

                        Log.i("1555", response.body().toString());

                        if (arr.length() == 0)
                            Toast.makeText(getActivity(), "There is No oppertinuty available...", Toast.LENGTH_LONG).show();


                        for (int i = 0; i < arr.length(); i++) {
                            Log.i("1555", response.body().toString());
                            JSONObject jb = arr.getJSONObject(i);

                            JSONObject imageObject = jb.getJSONObject("image");
                            JSONObject Orgnization = jb.getJSONObject("organization");
                            JSONObject Type = jb.getJSONObject("type");
                            String name = jb.getString("name");
                            int pin = jb.getInt("pins_count");
                            int ispin = jb.getInt("isPinned");
                            String image = imageObject.getString("mi");

                            String Oimage = Orgnization.getString("profile_pic");
                            String nameOrg = Orgnization.getString("name_ar");
                            System.out.println(nameOrg);
                            String catOrg = Type.getString("title");
                            System.out.println(Oimage);
                            String view = jb.getString("views_count");

                            String full = jb.getString("full_desc");


                            Names.add(name);
                            Pin.add(pin);
                            img.add(image);
                            Orgimage.add(Oimage);
                            OrgName.add(nameOrg);
                            ID.add(id);
                            OrgCat.add(catOrg);
                            viewnum.add(view);
                            isPinned.add(ispin);
                            Full.add(full);

                        }

                        mAdapter.notifyDataSetChanged();


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.i("1555", e.getMessage());
                    }


                } else {
                    Log.d("1555", "NOt success");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Big Big", "Wrong email or password");
            }
        });

    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private void setupWindowAnimations() {


        Fade fade = new Fade();
        View decor = getActivity().getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.toolbar), true);
        getActivity().getWindow().setEnterTransition(fade);
        getActivity().getWindow().setExitTransition(fade);

      /*  // Re-enter transition is executed when returning back to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT); // Use START if using right - to - left locale
        slideTransition.setDuration(1000);

       getActivity().getWindow().setReenterTransition(slideTransition);  // When MainActivity Re-enter the Screen
       getActivity(). getWindow().setExitTransition(slideTransition);     // When MainActivity Exits the Screen

        // For overlap of Re Entering Activity - MainActivity.java and Exiting TransitionActivity.java
        getActivity().getWindow().setAllowReturnTransitionOverlap(false);*/
    }
}