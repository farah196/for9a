package com.example.farahalkiswani.for9a;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Farah alkiswani on 7/12/2018.
 */

public class Adapter extends RecyclerView.Adapter <Adapter.ViewHolder> {

    List<String> Name;
    List<String> Desc;
    private LayoutInflater mInflater;

    public Adapter(Context context, List<String> name, List<String> Desc) {
        this.mInflater = LayoutInflater.from(context);
        this.Name = name;
        this.Desc = Desc;

    }

    public Adapter(List<String> List) {
        this.Name=List;

    }

    public List <String> getName() {
        return Name;
    }

    public void setName(List<String> name) {
        this.Name = name;
    }

    public List <String> getDesc() {
        return Desc;
    }

    public void setDesc(List <String> Desk) {
        this.Desc = Desk;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = mInflater.inflate(R.layout.recycle_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int position) {


        holder.name.setText( Name.get( position ) );
        holder.desc.setText( Desc.get(position ) );

    }


    @Override
    public int getItemCount() {

   return Name.size() ;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desc;

        public ViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.for9aname);
            desc = (TextView) view.findViewById(R.id.for9adesc);

        }
    }
    }

