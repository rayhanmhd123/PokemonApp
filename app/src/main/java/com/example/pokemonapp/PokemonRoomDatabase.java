package com.example.pokemonapp;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


@Database(entities = {Pokemon.class}, version = 1 , exportSchema = false)
public abstract class PokemonRoomDatabase extends RoomDatabase {
    public abstract PokemonDao tempatDao();
    private static volatile PokemonRoomDatabase INSTANCE;

    static PokemonRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PokemonRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            PokemonRoomDatabase.class, "pokemon_table").build();
                    PokemonRoomDatabase.class, "tempat_database").addCallback(sRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }


    private static Callback sRoomDatabaseCallback = new Callback(){
        @Override
        public void onOpen (@NonNull SupportSQLiteDatabase db){
            super.onOpen(db);
            new PopulateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PokemonDao mDao;

        PopulateDbAsync(PokemonRoomDatabase db) {
            mDao = db.tempatDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
//            mDao.deleteAllTempat();
            return null;
        }


    }


}
