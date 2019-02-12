package com.a2k.languagespeedup.database.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.a2k.languagespeedup.database.entities.ForeignPhrase;

import java.util.List;

@Dao
public interface ForeignPhraseDao {

    @Insert
    void insert(ForeignPhrase foreignPhrase);

    @Query("SELECT * FROM foreign_phrase_table WHERE deck_id = :id")
    LiveData<List<ForeignPhrase>> getForeignPhrasesByDeckId(long id);
}
