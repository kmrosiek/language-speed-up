package com.a2k.languagespeedup.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.a2k.languagespeedup.database.daos.CardDao;
import com.a2k.languagespeedup.database.daos.DeckDao;
import com.a2k.languagespeedup.database.entities.Card;
import com.a2k.languagespeedup.database.entities.Deck;


@Database(entities = {Deck.class, Card.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DeckDao deckDao();
    public abstract CardDao cardDao();

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private static final Object LOCK = new Object();
    private static AppDatabase instance;
    private static final String DATABASE_NAME = "dictionary_db";

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                context.deleteDatabase(DATABASE_NAME);
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }

        return instance;
    }

    //--------------------------------------------------------------------------------
    //------------------------------PRIVATE-METHODS-----------------------------------
    //--------------------------------------------------------------------------------

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DeckDao deckDao;

        private PopulateDbAsyncTask(AppDatabase db) {
            deckDao = db.deckDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            deckDao.insert(new Deck("Benjamin Franklin", Deck.ENGLISH));
            deckDao.insert(new Deck("Becoming", Deck.ENGLISH));
            deckDao.insert(new Deck("Amazing story of our country", Deck.ENGLISH));
            return null;
        }
    }



}

