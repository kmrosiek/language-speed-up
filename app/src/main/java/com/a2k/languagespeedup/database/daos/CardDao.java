package com.a2k.languagespeedup.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.a2k.languagespeedup.database.entities.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM card_table WHERE deck_id = :id")
    LiveData<List<Card>> getCardsByDeckId(int id);
}
