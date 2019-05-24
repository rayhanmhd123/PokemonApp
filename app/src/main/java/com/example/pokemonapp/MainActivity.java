package com.example.pokemonapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
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

public class MainActivity extends AppCompatActivity {
    int page = 1;

    private PokemonViewModel mTempatViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final PokemonListAdapter adapter = new PokemonListAdapter(this);
        recyclerView.setAdapter(adapter);
        adapter.setManager(getSupportFragmentManager());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(!recyclerView.canScrollVertically(1)){
//                    page = adapter.getCount()%10;
                    page = page+1;
                    loadPokemon(page);
                }
            }
        });

        mTempatViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        mTempatViewModel.getAllTempat().observe(this, new Observer<List<Pokemon>>() {
            @Override
            public void onChanged(@Nullable final List<Pokemon> tempat) {
                // Update the cached copy of the words in the adapter.
                adapter.setTempat(tempat);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.delete) {
            mTempatViewModel.deleteAllTempat();
            page=1;
            loadPokemon(page);
            Toast.makeText(getApplicationContext(), "Berhasil hapus semua data", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.card) {
//            mTempatViewModel.deleteAllTempat();

            Toast.makeText(getApplicationContext(), "Menampilkan Berdasarkan Card", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.subtipe) {
//            mTempatViewModel.deleteAllTempat();

            Toast.makeText(getApplicationContext(), "Menampilkan Berdasarkan SubTipe", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.tipe) {
//            mTempatViewModel.deleteAllTempat();
            Toast.makeText(getApplicationContext(), "Menampilkan Berdasarkan Tipe", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (id == R.id.supertipe) {
//            mTempatViewModel.deleteAllTempat();
            Toast.makeText(getApplicationContext(), "Menampilkan Berdasarkan SuperTipe", Toast.LENGTH_SHORT).show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void loadPokemon(int page) {
        String JSON_URL = "https://api.pokemontcg.io/v1/cards?page="+page+"&pageSize=10";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray playerArray = obj.getJSONArray("cards");


                            for (int i = 0; i < playerArray.length(); i++) {

//                                int k = playerArray.length();

                                JSONObject playerObject = playerArray.getJSONObject(i);

                                String id=playerObject.getString("id");
                                String Nama=playerObject.getString("name");
                                String imageUrl=playerObject.getString("imageUrl");

                                String type = "haha";
                                JSONArray types = playerObject.getJSONArray("");
                                for (int k=0; k<types.length(); k++){
                                    types.length();
                                    type.concat(types.getString(k));
                                }


                                String supertype=playerObject.getString("supertype");
                                String subtype=playerObject.getString("subtype");

                                Pokemon pokemon = new Pokemon(id,Nama,imageUrl,type,supertype,subtype);
                                mTempatViewModel.insert(pokemon);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
