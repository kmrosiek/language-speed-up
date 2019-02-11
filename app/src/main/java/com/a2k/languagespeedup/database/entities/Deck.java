package com.a2k.languagespeedup.database.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "deck_table")
public class Deck {

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    public static int ENGLISH = 1;

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private int language;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public Deck(final String name, final int language) {
        this.name = name;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getLanguage() {
        return language;
    }
}

