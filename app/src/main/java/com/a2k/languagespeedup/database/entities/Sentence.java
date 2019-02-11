package com.a2k.languagespeedup.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "sentence_table", foreignKeys = @ForeignKey(entity = ForeignPhrase.class,
        parentColumns = "id", childColumns = "foreign_phrase_id", onDelete = ForeignKey.CASCADE), indices = @Index("foreign_phrase_id"))
public class Sentence {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "foreign_phrase_id")
    private int foreignPhraseId;

    private String foreignSentence;

    private String nativeSentence;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public Sentence(final int foreignPhraseId, final String foreignSentence,
                    final String nativeSentence) {
        this.foreignPhraseId = foreignPhraseId;
        this.foreignSentence = foreignSentence;
        this.nativeSentence = nativeSentence;
    }

    //--------------------------------------------------------------------------------
    //---------------------------GETTERS-AND-SETTERS----------------------------------
    //--------------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getForeignPhraseId() {
        return foreignPhraseId;
    }

    public String getForeignSentence() {
        return foreignSentence;
    }

    public String getNativeSentence() {
        return nativeSentence;
    }
}
