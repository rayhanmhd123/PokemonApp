package com.example.pokemonapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pokemon_table")
public class Pokemon implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private Long id;
    @ColumnInfo(name = "id_pokemon")
    private String id_pokemon;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "imageUrl")
    private String imageUrl;
    @ColumnInfo(name = "type")
    private String type;
    @ColumnInfo(name = "subtype")
    private String subtype;
    @ColumnInfo(name = "supertype")
    private String supertype;

    public Pokemon(String id_pokemon, String name, String imageUrl, String type, String subtype, String supertype) {
        this.id_pokemon = id_pokemon;
        this.name = name;
        this.imageUrl = imageUrl;
        this.type = type;
        this.subtype = subtype;
        this.supertype = supertype;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    String getId_pokemon() {
        return id_pokemon;
    }

    public String getName() {
        return name;
    }

    String getImageUrl() {
        return imageUrl;
    }

    public String getType() {
        return type;
    }

    String getSubtype() {
        return subtype;
    }

    String getSupertype() {
        return supertype;
    }
}
