package com.a2k.languagespeedup.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.a2k.languagespeedup.database.daos.CardDao;
import com.a2k.languagespeedup.database.daos.DeckDao;
import com.a2k.languagespeedup.database.entities.Card;
import com.a2k.languagespeedup.database.entities.Deck;

import java.util.List;

public class AppRepository {

    //--------------------------------------------------------------------------------
    //-----------------------------PRIVATE-MEMBERS------------------------------------
    //--------------------------------------------------------------------------------

    private DeckDao deckDao;
    private CardDao cardDao;
    private LiveData<List<Deck>> allDecks;

    //--------------------------------------------------------------------------------
    //------------------------------PUBLIC-METHODS------------------------------------
    //--------------------------------------------------------------------------------

    public AppRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        deckDao = database.deckDao();
        cardDao = database.cardDao();
        allDecks = deckDao.getAllDecks();
    }

    public LiveData<List<Deck>> getAllDecks(){
        return allDecks;
    }

    public void insertDeck(Deck newDeck) {
        new InsertNewDeckAsyncTask(deckDao).execute(newDeck);
    }

    public LiveData<List<Card>> getCardsByDeckId(Integer id) {
        return cardDao.getCardsByDeckId(id);
    }

    public void fetchCardsByDeckId(final int id) {
        GetCardsByDeckIdAsyncTask task = new GetCardsByDeckIdAsyncTask(cardDao);
        task.execute(id);
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

    private static class GetCardsByDeckIdAsyncTask extends AsyncTask<Integer, Void, LiveData<List<Card>>> {

        private CardDao cardDao;

        private GetCardsByDeckIdAsyncTask(CardDao cardDao) {
            this.cardDao = cardDao;
        }

        @Override
        protected LiveData<List<Card>> doInBackground(Integer... deckId) {
            return cardDao.getCardsByDeckId(deckId[0]);
        }
    }


}
