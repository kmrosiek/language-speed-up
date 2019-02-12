package com.a2k.languagespeedup.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.a2k.languagespeedup.database.entities.Sentence;

import java.util.List;

@Dao
public interface SentenceDao {

    @Insert
    void insert(Sentence sentence);

    @Query("SELECT * FROM sentence_table WHERE foreign_phrase_id IN (:ids)")
    List<Sentence> getSentencesByForeignPhraseId(List<Long> ids);
}
