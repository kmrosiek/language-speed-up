package com.example.languagespeedup.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.languagespeedup.database.daos.DeckDao;
import com.example.languagespeedup.database.entities.Deck;

import java.util.List;

public class AppRepository {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private DeckDao deckDao;
    private LiveData<List<Deck>> allDecks;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        deckDao = database.deckDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks(){
        return allDecks;
    }

    public void insertDeck(Deck newDeck) {
        new InsertNewDeckAsyncTask(deckDao).execute(newDeck);
    }


    //--------------------------------------------------------------------------------
    //------------------------------PRIVATE-METHODS-----------------------------------
    //--------------------------------------------------------------------------------

    private static class InsertNewDeckAsyncTask extends AsyncTask<Deck, Void, Void> {

        private DeckDao deckDao;

        private InsertNewDeckAsyncTask(DeckDao deckDao) {
            this.deckDao = deckDao;
        }

        @Override
        protected Void doInBackground(Deck... newDecks) {
            deckDao.insert(newDecks[0]);
            return null;
        }
    }


}
