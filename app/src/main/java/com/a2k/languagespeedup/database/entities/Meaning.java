package com.a2k.languagespeedup.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "meaning_table", foreignKeys = @ForeignKey(entity = ForeignPhrase.class,
        parentColumns = "id", childColumns = "foreign_phrase_id", onDelete = ForeignKey.CASCADE), indices = @Index("foreign_phrase_id"))
public class Meaning {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "foreign_phrase_id")
    private long foreignPhraseId;

    private String nativePhrase;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public Meaning(final long foreignPhraseId, final String nativePhrase) {
        this.foreignPhraseId = foreignPhraseId;
        this.nativePhrase = nativePhrase;
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

    public long getForeignPhraseId() {
        return foreignPhraseId;
    }

    public String getNativePhrase() {
        return nativePhrase;
    }
}
