package com.a2k.languagespeedup.database.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.a2k.languagespeedup.database.entities.Meaning;

import java.util.List;

@Dao
public interface MeaningDao {

    @Insert
    void insert(Meaning meaning);

    @Query("SELECT * FROM meaning_table WHERE foreign_phrase_id IN (:ids)")
    List<Meaning> getMeaningsByForeignPhraseId(List<Long> ids);
}
