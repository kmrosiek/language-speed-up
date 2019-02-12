package com.a2k.languagespeedup.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "foreign_phrase_table", foreignKeys = @ForeignKey(entity = Deck.class,
        parentColumns = "id", childColumns = "deck_id", onDelete = ForeignKey.CASCADE), indices = @Index("deck_id"))
public class ForeignPhrase {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "deck_id")
    private long deckId;

    private String foreignText;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public ForeignPhrase(final long deckId, final String foreignText) {
        this.deckId = deckId;
        this.foreignText = foreignText;
    }

    //--------------------------------------------------------------------------------
    //---------------------------GETTERS-AND-SETTERS----------------------------------
    //--------------------------------------------------------------------------------

    public long getDeckId() {
        return deckId;
    }

    public String getForeignText() {
        return foreignText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
