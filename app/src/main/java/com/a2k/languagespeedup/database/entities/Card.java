package com.a2k.languagespeedup.database.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "card_table", foreignKeys = @ForeignKey(entity = Deck.class,
        parentColumns = "id", childColumns = "deck_id", onDelete = ForeignKey.CASCADE), indices = @Index("deck_id"))
public class Card {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "deck_id")
    private int deckId;

    private String foreignText;

    //--------------------------------------------------------------------------------
    //---------------------------GETTERS-AND-SETTERS----------------------------------
    //--------------------------------------------------------------------------------

    public int getDeckId() {
        return deckId;
    }

    public void setDeckId(int deckId) {
        this.deckId = deckId;
    }

    public String getForeignText() {
        return foreignText;
    }

    public void setForeignText(String foreignText) {
        this.foreignText = foreignText;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
