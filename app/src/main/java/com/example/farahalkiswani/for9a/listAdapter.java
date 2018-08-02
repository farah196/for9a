package com.example.farahalkiswani.for9a;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder> {

    List<String> Name;
    List<String> title;
    List<String> image;
    List<String> Orgimage;
    List<Integer> id;
    List<Integer> pinNumber;
    List<Integer> isPinned;
    List<String> views;
    List<String> FullDescription;
    List<String> OrgName;
    List<String> orgCat;
    Context context;

    private LayoutInflater mInflater;

    public listAdapter(Context context, List<String> name, List<String> Desc, List<String> image, List<Integer> pinNumber, List<Integer> id,
                       List<Integer> isPinned, List<String> views, List<String> Orgimage, List<String> FullDescription, List<String> OrgName) {
        this.mInflater = LayoutInflater.from(context);
        this.Name = name;
        this.title = Desc;
        this.image = image;
        this.pinNumber = pinNumber;
        this.id = id;
        this.isPinned = isPinned;
        this.context = context;
        this.views = views;
        this.Orgimage = Orgimage;
        this.FullDescription = FullDescription;
        this.OrgName = OrgName;

    }

    public listAdapter(List<String> List) {
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

    public void userPin(String auth, int Id) {

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


    public listAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = mInflater.inflate(R.layout.recycle_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final listAdapter.ViewHolder holder, final int position) {


        holder.name.setText(Name.get(position));
        holder.name.setTypeface(null, Typeface.BOLD);
        holder.name.setTypeface(Typeface.SANS_SERIF);
        holder.PinNumber.setText(String.valueOf(pinNumber.get(position)));
        holder.viewnump.setText(views.get(position));
        holder.imgPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = context.getSharedPreferences("auth", MODE_PRIVATE);
                String authintication = prefs.getString("authclient", "");
                Log.d("authpin", authintication);

                if (isPinned.get(position) == 0) {


                    userPin(authintication, id.get(position));
                    holder.PinNumber.setText(String.valueOf(pinNumber.get(position) + 1));
                    holder.imgPin.setImageResource(R.drawable.likepin);
                    Toast.makeText(context, "save opportunity", Toast.LENGTH_LONG).show();

                } else {

                    DeletePin(authintication, id.get(position));
                    holder.PinNumber.setText(String.valueOf(pinNumber.get(position) - 1));
                    holder.imgPin.setImageResource(R.drawable.like);
                    Toast.makeText(context, "unsave opportunity", Toast.LENGTH_LONG).show();


                }

            }
        });

        // ((Activity)context). getWindow().setAllowReturnTransitionOverlap(false);
        Picasso.with(context).load(image.get(position)).into(holder.getImage());

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("name", Name.get(position));
                intent.putExtra("img", image.get(position));
                intent.putExtra("OrgImg", Orgimage.get(position));
                intent.putExtra("full", FullDescription.get(position));
                intent.putExtra("OrgName", OrgName.get(position));
//                intent.putExtra("Orgcat",orgCat.get(position));
                // Get the transition name from the string
                Pair[] pair = new Pair[2];
                pair[0] = new Pair<View, String>(holder.name, "title_shared");
                pair[1] = new Pair<View, String>(holder.img1, "image_shared");

                ActivityOptions options =

                        ActivityOptions.makeSceneTransitionAnimation((Activity) context, pair);

                context.startActivity(intent, options.toBundle());
            }
        });


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
        public TextView name, PinNumber, viewnump;
        ImageView img1, imgPin, imgOrg;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.title1);
            img1 = view.findViewById(R.id.image1);
            imgPin = view.findViewById(R.id.imageView41);
            PinNumber = view.findViewById(R.id.pinNumber1);
            viewnump = view.findViewById(R.id.viewnum1);


        }


        public ImageView getImage() {
            return img1;
        }


    }
}

