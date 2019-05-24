package com.example.pokemonapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public class PokemonFragment extends Fragment {
    private Pokemon tempat;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public Pokemon getTempat() {
        return tempat;
    }

    public void setTempat(Pokemon tempat) {
        this.tempat = tempat;
    }
}
