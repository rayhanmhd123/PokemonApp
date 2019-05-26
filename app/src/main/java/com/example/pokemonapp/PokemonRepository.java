package com.example.pokemonapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

class PokemonRepository {

    private PokemonDao mTempatDao;
    private LiveData<List<Pokemon>> mALLTempat;

    PokemonRepository(Application application) {
        PokemonRoomDatabase db = PokemonRoomDatabase.getDatabase(application);
        mTempatDao = db.tempatDao();
        mALLTempat = mTempatDao.getAllTempat();
    }

    LiveData<List<Pokemon>> getALLTempat() {
        return mALLTempat;
    }

    void insert(Pokemon tempat) {
        new insertAsyncTask(mTempatDao).execute(tempat);
    }
    void deleteAllTempat() {
        new deleteAllTempatAsyncTask(mTempatDao).execute();
    }

    private static class insertAsyncTask extends AsyncTask<Pokemon, Void, Void> {

        private PokemonDao mAsyncTaskDao;

        private insertAsyncTask(PokemonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pokemon... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAllTempatAsyncTask extends AsyncTask<Pokemon, Void, Void> {

        private PokemonDao mAsyncTaskDao;

        private deleteAllTempatAsyncTask(PokemonDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Pokemon... params) {
            mAsyncTaskDao.deleteAllTempat();
            return null;
        }
    }
}
