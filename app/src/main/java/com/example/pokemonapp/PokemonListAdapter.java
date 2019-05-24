package com.example.pokemonapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

public class PokemonListAdapter extends RecyclerView.Adapter<PokemonListAdapter.TempatViewHolder> {
    private FragmentManager manager;
    private final LayoutInflater mInflater;
    private List<Pokemon> mTempat; // Cached copy of words
    private int count;



    void setManager(FragmentManager manager){
        this.manager= manager;
    }

    class TempatViewHolder extends RecyclerView.ViewHolder {
        private final TextView nama;
        private final TextView type;
        private final ImageView image;
        private final LinearLayout parentlayout;

        TempatViewHolder(@NonNull View itemView) {
            super(itemView);
            parentlayout = itemView.findViewById(R.id.parentlayout);
            nama = itemView.findViewById(R.id.nama);
            type = itemView.findViewById(R.id.type);
            image = itemView.findViewById(R.id.image);
        }
    }


    PokemonListAdapter(Context context ){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TempatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new TempatViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final TempatViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (mTempat != null) {
            Pokemon current = mTempat.get(position);
            holder.nama.setText(current.getName());
            holder.type.setText(current.getType());
            Glide.with(mInflater.getContext()).load(current.getImageUrl()).into(holder.image);

            holder.parentlayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog();
                    dialog.setTempat(mTempat.get(position));
                    dialog.show(manager,"tampil");
                }
            });
        } else {
            Toast.makeText(mInflater.getContext(), "Sukses Menambahkan Data", Toast.LENGTH_SHORT).show();
        }
    }

    void setTempat(List<Pokemon> tempats){
        mTempat = tempats;
        notifyDataSetChanged();
    }

    Pokemon getTempatat(int position){
        return mTempat.get(position);
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mTempat != null)
            return mTempat.size();
        else return 0;
    }

    public int getCount() {
        return mTempat.size();
    }
}
