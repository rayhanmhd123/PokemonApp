package com.example.pokemonapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class Dialog extends DialogFragment {
    ImageView image;
    TextView nama;
    TextView type;
    TextView subtype;
    TextView supertype;
    Pokemon tempat;

    public void setTempat(Pokemon tempat) {
        this.tempat = tempat;
    }

    @NonNull
    @Override
    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.gede, null);
        builder.setView(view);
        image = view.findViewById(R.id.image);
        nama = view.findViewById(R.id.nama);
        type = view.findViewById(R.id.type);
        subtype = view.findViewById(R.id.subtype);
        supertype = view.findViewById(R.id.supertype);
        Glide.with(inflater.getContext()).load(tempat.getImageUrl()).into(image);
        nama.setText("Nama :  " + tempat.getName());
        type.setText("Type :  " +tempat.getType());
        subtype.setText("SubType :  " +tempat.getSubtype());
        supertype.setText("SuperType :  " +tempat.getSupertype());
        return builder.create();
    }
}
