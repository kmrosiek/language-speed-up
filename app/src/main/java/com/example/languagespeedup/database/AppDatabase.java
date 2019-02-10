package com.example.languagespeedup.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.languagespeedup.database.daos.DeckDao;
import com.example.languagespeedup.database.entities.Deck;


@Database(entities = {Deck.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract DeckDao deckDao();

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private static final Object LOCK = new Object();
    private static AppDatabase instance;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    static AppDatabase getInstance(Context context) {
        if(instance == null) {
            synchronized (LOCK) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, "diki_database").fallbackToDestructiveMigration()
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
            deckDao.insert(new Deck("Benjamin Franklin"));
            deckDao.insert(new Deck("Becoming"));
            deckDao.insert(new Deck("Amazing story of our country"));
            return null;
        }
    }



}

