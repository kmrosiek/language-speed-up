package com.example.languagespeedup.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.languagespeedup.database.entities.Deck;

import java.util.List;

@Dao
public interface DeckDao {

    @Insert
    void insert(Deck deck);

    @Update
    void update(Deck deck);

    @Delete
    void delete(Deck deck);

    @Query("SELECT * FROM deck_table")
    LiveData<List<Deck>> getAllDecks();
}

