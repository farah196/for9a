package com.example.farahalkiswani.for9a;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class Home extends Fragment {
    ArrayList<String> Names = new ArrayList<>();
    ArrayList<String> Desc = new ArrayList<>();
    ArrayList<String> img = new ArrayList<>();
    ArrayList<Integer> Pin = new ArrayList<>();
    ArrayList<Integer> ID = new ArrayList<>();
    ArrayList<Integer> isPinned = new ArrayList<>();
    int id = 0;
    Adapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        mAdapter = new Adapter(getActivity(), Names, Desc, img, Pin, ID, isPinned);
        recyclerView.setAdapter(mAdapter);





        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new Home.GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mAdapter = new Adapter(getActivity().getApplicationContext(), Names, Desc, img,Pin,ID,isPinned);

        recyclerView.setAdapter(mAdapter);

        getOppertinuity(10, id);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }


            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                getOppertinuity(10, id);

            }
        });


        return rootView;
    }

    private void getOppertinuity(int i,  int Id) {


        Log.d("status", "get Oppertinuty");


        SharedPreferences prefs =getActivity(). getSharedPreferences("auth", MODE_PRIVATE);
        String authintication = prefs.getString("authclient", "MNrAG2QLv41sWs2qd-spRKT594bfwROM");
        APIService1 service = ApiClientHeader.getClient(authintication).create(APIService1.class);

        Call<JsonObject> userCall = service.getTimeline(authintication, i, Id);

        userCall.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.i("getTimeSessions", Integer.toString(response.code()));
                JSONObject obj = null;
                if (response.isSuccessful()) {

                    try {
                        obj = new JSONObject(String.valueOf(response.body()));
                        JSONArray arr = obj.getJSONArray("results");


                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject jb = arr.getJSONObject(i);
                            JSONObject imageObject = jb.getJSONObject("image");
                            String name = jb.getString("name");
                            int pin = jb.getInt("pins_count");
                            int ispin = jb.getInt("isPinned");
                            String image = imageObject.getString("mi");
                            int id = jb.getInt("id");

                            Names.add(name);
                            Pin.add(pin);
                            img.add(image);
                            ID.add(id);
                            isPinned.add(ispin);

                        }
                        mAdapter.notifyDataSetChanged();

                        /*JSONObject IDObj = arr.getJSONObject(arr.length() - 1);

                        int ID;
                        ID = IDObj.getInt("id");*/




                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                } else {
                    Log.d("Errorblaaa", "NOt success");


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



    }
