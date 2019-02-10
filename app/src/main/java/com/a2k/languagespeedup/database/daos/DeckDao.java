package com.a2k.languagespeedup.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.a2k.languagespeedup.database.entities.Deck;

import java.util.List;

@Dao
public interface DeckDao {

    @Insert
    void insert(Deck deck);

    @Query("SELECT * FROM deck_table")
    LiveData<List<Deck>> getAllDecks();
}

