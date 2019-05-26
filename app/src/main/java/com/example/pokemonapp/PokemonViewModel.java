package com.example.pokemonapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class PokemonViewModel extends AndroidViewModel {
    private PokemonRepository mRepository;
    private LiveData<List<Pokemon>> mAllTempat;

    public PokemonViewModel(Application application) {
        super(application);
        mRepository = new PokemonRepository(application);
        mAllTempat = mRepository.getALLTempat();
    }

    LiveData<List<Pokemon>> getAllTempat() {
        return mAllTempat;
    }

    void insert(Pokemon tempat) {
        mRepository.insert(tempat);
    }

    void deleteAllTempat() {
        mRepository.deleteAllTempat();
    }

}
