package com.a2k.languagespeedup.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a2k.languagespeedup.database.daos.ForeignPhraseDao;
import com.a2k.languagespeedup.database.daos.DeckDao;
import com.a2k.languagespeedup.database.entities.ForeignPhrase;
import com.a2k.languagespeedup.database.entities.Deck;

import java.util.List;

public class AppRepository {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private DeckDao deckDao;
    private ForeignPhraseDao foreignPhraseDao;
    private LiveData<List<Deck>> allDecks;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        deckDao = database.deckDao();
        foreignPhraseDao = database.foreignTextDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks(){
        return allDecks;
    }

    public void insertDeck(Deck newDeck) {
        new InsertNewDeckAsyncTask(deckDao).execute(newDeck);
    }

    public LiveData<List<ForeignPhrase>> getForeignPhrasesForDeckId(Long deckId) {
        return foreignPhraseDao.getForeignPhrasesByDeckId(deckId);
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
