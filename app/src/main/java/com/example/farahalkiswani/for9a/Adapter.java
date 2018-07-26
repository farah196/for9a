package com.example.farahalkiswani.for9a;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Farah alkiswani on 7/12/2018.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<String> Name;
    List<String> title;
    List<String> image;
    List<String> Orgimage;
    List<Integer> id;
    List<Integer> pinNumber;
    List<Integer> isPinned;
    List<String> views;
    List<String> FullDescription;
    Context context;

    private FragmentCommunication mCommunicator;
    private LayoutInflater mInflater;

    public Adapter(Context context, List<String> name, List<String> Desc, List<String> image, List<Integer> pinNumber, List<Integer> id, List<Integer> isPinned) {
        this.mInflater = LayoutInflater.from(context);
        this.Name = name;
        this.title = Desc;
        this.image = image;
        this.pinNumber = pinNumber;
        this.id = id;
        this.isPinned = isPinned;
        this.context = context;

    }

    public Adapter(List<String> List) {
        this.Name = List;

    }

    public List<String> getName() {
        return Name;
    }

    public void setName(List<String> name) {
        this.Name = name;
    }

    public List<String> getDesc() {
        return title;
    }

    public void setDesc(List<String> Desk) {
        this.title = Desk;
    }

    public void userPin(String auth,int Id) {

        Log.d("status", "get pin");

        APIService1 service = ApiClientHeader.getClient(auth).create(APIService1.class);

        Call<JsonObject> userCall = service.Pin(auth, Id);

        userCall.enqueue(new Callback<JsonObject>() {

            @Override
            public void onResponse(@NonNull Call<JsonObject> call, @NonNull Response<JsonObject> response) {
                Log.i("getTimeSessions", Integer.toString(response.code()));

                if (response.isSuccessful()) {
                    Log.d("success", " success");

                } else {
                    Log.d("Errorblaaa123", "NOt success");


                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("Errorblaaa", "NOt success");
            }
        });
    }


    public void DeletePin(String auth, int Id) {

        APIService1 service = ApiClientHeader.getClient(auth).create(APIService1.class);

        Call<JsonObject> userCall = service.DeletPin(auth, Id);
        userCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("getTimeSessions", Integer.toString(response.code()));

                if (response.isSuccessful()) {
                    Log.d("200", "SUCCESS");

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("111", "NOTSUCCESS");
            }
        });
    }

    @NonNull
    @Override


    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_row, parent, false);
            ViewHolder vh = new ViewHolder(v);
            return vh;

          /*  //  View view = mInflater.inflate(R.layout.recycle_row, parent, false);
        RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view,mCommunicator);
        return holder;*/
    }

    @Override
    public void onBindViewHolder(@NonNull final Adapter.ViewHolder holder, final int position) {


        holder.name.setText(Name.get(position));
        holder.name.setTypeface(null, Typeface.BOLD);
        holder.name.setTypeface(Typeface.SANS_SERIF);
        holder.PinNumber.setText(String.valueOf(pinNumber.get(position)));
        holder.imgPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = context.getSharedPreferences("auth", MODE_PRIVATE);
                String authintication = prefs.getString("authclient", "");
                Log.d("authpin", authintication);

                if (isPinned.get(position) == 0) {


                    userPin(authintication,id.get(position));
                    holder.PinNumber.setText(String.valueOf(pinNumber.get(position)+1));
                    holder.imgPin.setImageResource(R.drawable.likepin);
                    Toast.makeText(context,"تم حفظ الفرصة",Toast.LENGTH_LONG).show();

                }


                else {

                    DeletePin(authintication, id.get(position));
                    holder.PinNumber.setText(String.valueOf(pinNumber.get(position)-1));
                    holder.imgPin.setImageResource(R.drawable.like);
                    Toast.makeText(context,"تم الغاء حفظ الفرصة",Toast.LENGTH_LONG).show();

                }

            }
        });
        Picasso.with(context).load(image.get(position)).into(holder.getImage());




    }


    @Override
    public int getItemCount() {

        return Name.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, PinNumber;
        ImageView img1, imgPin;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title);
            img1 = view.findViewById(R.id.image);
            imgPin = view.findViewById(R.id.imageView4);
            PinNumber = view.findViewById(R.id.pinNumber);


        }



        public ImageView getImage() {
            return img1;
        }


    }
}

