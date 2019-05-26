package com.example.pokemonapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.Objects;

public class SetDialog extends DialogFragment {

    List<String> type;
    List<String> subtype;
    List<String> supertype;
    Spinner spinner;
    Spinner spinner2;
    Spinner spinner3;
    String types;
    String subtypes;
    String supertypes;

    public void setTypes(String types) {
        this.types = types;
    }

    public void setSubtypes(String subtypes) {
        this.subtypes = subtypes;
    }

    public void setSupertypes(String supertypes) {
        this.supertypes = supertypes;
    }

    public void setType(List<String> type) {
        this.type = type;
    }

    public void setSubtype(List<String> subtype) {
        this.subtype = subtype;
    }

    public void setSupertype(List<String> supertype) {
        this.supertype = supertype;
    }

    SetDialogListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (SetDialogListener) context;
    }

    public interface SetDialogListener {
        void onDialogPositiveClick(String type, String subtype, String supertype);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.setting, null);
        builder.setView(view);
        spinner = view.findViewById(R.id.spinner);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, subtype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, supertype);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter2);
        spinner3.setAdapter(adapter3);
        if (!"Kosong".equals(types)){
            int position = adapter.getPosition(types);
            spinner.setSelection(position);
        }
        if (!"Kosong".equals(subtypes)){
            int position = adapter2.getPosition(subtypes);
            spinner2.setSelection(position);
        }
        if (!"Kosong".equals(supertypes)){
            int position = adapter3.getPosition(supertypes);
            spinner3.setSelection(position);
        }
        builder.setPositiveButton("Save Setting", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onDialogPositiveClick(spinner.getSelectedItem().toString(),spinner2.getSelectedItem().toString(),spinner3.getSelectedItem().toString());
                Toast.makeText(getActivity(), "save ", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Cancel ", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();
    }
}
