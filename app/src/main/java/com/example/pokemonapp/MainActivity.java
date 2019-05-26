package com.example.pokemonapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class MainActivity extends AppCompatActivity implements SetDialog.SetDialogListener{
    int page = 1;
    private PokemonViewModel mTempatViewModel;
    private List<String> alltype;
    private List<String> allsubtype;
    private List<String> allsupertype;
    private String type;
    private String subtype;
    private String supertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        alltype = new ArrayList<>();
        allsubtype = new ArrayList<>();
        allsupertype = new ArrayList<>();
        alltype.add("Kosong");
        allsubtype.add("Kosong");
        allsupertype.add("Kosong");
        type = "Kosong";
        subtype = "Kosong";
        supertype = "Kosong";
        loadtype();
        loadsubtype();
        loadsupertype();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(alltype != null && allsubtype != null && allsupertype != null){
                    SetDialog dialog = new SetDialog();
                    dialog.setType(alltype);
                    dialog.setSubtype(allsubtype);
                    dialog.setSupertype(allsupertype);
                    dialog.setTypes(type);
                    dialog.setSubtypes(subtype);
                    dialog.setSupertypes(supertype);
                    dialog.show(getSupportFragmentManager(), "pokemon");
                }
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
                    page = (adapter.getCount()/10) + 1;
                    if(adapter.getCount()%10 < 1){
                        loadPokemon();
                    }
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
//            loadPokemon();
            Toast.makeText(getApplicationContext(), "Berhasil hapus semua data", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadPokemon() {
        String JSON_URL;
        if(!type.equals("Kosong") && subtype.equals("Kosong") && supertype.equals("Kosong")){
            JSON_URL = "https://api.pokemontcg.io/v1/cards?types="+type+"&page="+page+"&pageSize=10";
//            mTempatViewModel.deleteAllTempat();
        }else{
            if(type.equals("Kosong") && !subtype.equals("Kosong") && supertype.equals("Kosong")){
//                JSON_URL = "https://api.pokemontcg.io/v1/cards?subtype="+subtype+"&page="+page+"&pageSize=10";
                JSON_URL = "https://api.pokemontcg.io/v1/cards?page="+page+"&pageSize=10&subtype="+subtype.replaceAll(" ","%20");
//                mTempatViewModel.deleteAllTempat();
            }else {
                if(type.equals("Kosong") && subtype.equals("Kosong") && !supertype.equals("Kosong")){
                    JSON_URL = "https://api.pokemontcg.io/v1/cards?supertype="+supertype+"&page="+page+"&pageSize=10";
//                    mTempatViewModel.deleteAllTempat();
                }else {
                    if(!type.equals("Kosong") && !subtype.equals("Kosong") && supertype.equals("Kosong")){
                        JSON_URL = "https://api.pokemontcg.io/v1/cards?subtype="+subtype.replaceAll(" ","%20")+"&types="+type+"&page="+page+"&pageSize=10";
//                        mTempatViewModel.deleteAllTempat();
                    }else {
                        if(!type.equals("Kosong") && subtype.equals("Kosong") && !supertype.equals("Kosong")){
                            JSON_URL = "https://api.pokemontcg.io/v1/cards?supertype="+supertype+"&types="+type+"&page="+page+"&pageSize=10";
//                            mTempatViewModel.deleteAllTempat();
                        }else {
                            if(type.equals("Kosong") && !subtype.equals("Kosong") && !supertype.equals("Kosong")){
                                JSON_URL = "https://api.pokemontcg.io/v1/cards?supertype="+supertype+"&subtype="+subtype.replaceAll(" ","%20")+"&page="+page+"&pageSize=10";
//                                mTempatViewModel.deleteAllTempat();
                            }else{
                                if(!type.equals("Kosong") && !subtype.equals("Kosong") && !supertype.equals("Kosong")){
                                    JSON_URL = "https://api.pokemontcg.io/v1/cards?supertype="+supertype+"&subtype="+subtype.replaceAll(" ","%20")+"&types="+type+"&page="+page+"&pageSize=10";
//                                    mTempatViewModel.deleteAllTempat();
                                }else {
                                    JSON_URL = "https://api.pokemontcg.io/v1/cards?page="+page+"&pageSize=10";
//                                    mTempatViewModel.deleteAllTempat();
                                }
                            }
                        }
                    }
                }
            }
        }
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray playerArray = obj.getJSONArray("cards");
                            for (int i = 0; i < playerArray.length(); i++) {
                                JSONObject playerObject = playerArray.getJSONObject(i);
                                String id=playerObject.getString("id");
                                String Nama=playerObject.getString("name");
                                String imageUrl=playerObject.getString("imageUrl");
                                String typek;
                                try {
                                    JSONArray types = playerObject.getJSONArray("types");
                                    StringBuilder typekBuilder = new StringBuilder();
                                    for (int k = 0; k < types.length() ; k++){
                                        String temp = types.getString(k);
                                        if (k == 0){
                                            typekBuilder = new StringBuilder(temp);
                                        }else {
                                            typekBuilder.append(", ").append(temp);
                                        }
                                    }
                                    typek = typekBuilder.toString();
                                }catch (Exception e){
                                    typek = "Dont have any type";
                                }
                                String supertype=playerObject.getString("supertype");
                                String subtype=playerObject.getString("subtype");
                                Pokemon pokemon = new Pokemon(id,Nama,imageUrl,typek,supertype,subtype);
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

    private void loadtype() {
        String JSON_URL= "https://api.pokemontcg.io/v1/types";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray types = obj.getJSONArray("types");
                            for (int k=0; k<types.length(); k++){
                                alltype.add(types.getString(k));
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

    private void loadsubtype() {
        String JSON_URL= "https://api.pokemontcg.io/v1/subtypes";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray types = obj.getJSONArray("subtypes");
                            for (int k=0; k<types.length(); k++){
                                allsubtype.add(types.getString(k));
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

    private void loadsupertype() {
        String JSON_URL= "https://api.pokemontcg.io/v1/supertypes";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, JSON_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray types = obj.getJSONArray("supertypes");
                            for (int k=0; k<types.length(); k++){
                                allsupertype.add(types.getString(k));
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

    @Override
    public void onDialogPositiveClick(String type, String subtype, String supertype) {
        this.type = type;
        this.subtype = subtype;
        this.supertype = supertype;
        mTempatViewModel.deleteAllTempat();
        loadPokemon();
        Toast.makeText(getApplicationContext(), "Request " + type+" "+ subtype +" "+ supertype, Toast.LENGTH_SHORT).show();
    }
}
